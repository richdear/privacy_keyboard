package com.richdear.privacykeyboard.inputmethod.latin.e2ee;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.richdear.privacykeyboard.inputmethod.latin.e2ee.util.HTMLFormatter;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.MessageFrame;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.MessageType;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.SignalProtocolMain;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.Contact;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.MessageStorage;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.EncodingHelper;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.Encoder;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.StegoEncoder;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.encoding.BraveEncoder;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactDuplicatedException;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactIncorrectException;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.CharacterOverflowException;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.ContactUnknownException;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.MessageUnknownException;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.JsonUtil;

import org.signal.libsignal.protocol.DuplicateMessageException;
import org.signal.libsignal.protocol.InvalidKeyException;
import org.signal.libsignal.protocol.InvalidKeyIdException;
import org.signal.libsignal.protocol.InvalidMessageException;
import org.signal.libsignal.protocol.InvalidVersionException;
import org.signal.libsignal.protocol.LegacyMessageException;
import org.signal.libsignal.protocol.NoSessionException;
import org.signal.libsignal.protocol.SignalProtocolAddress;
import org.signal.libsignal.protocol.UntrustedIdentityException;
import org.signal.libsignal.protocol.fingerprint.Fingerprint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class E2EEMenu {
  private static final String TAG = E2EEMenu.class.getSimpleName();

  private final Context messgaeContext;

  private final String INFO_CONTACT_ALREADY_EXISTS = "Contact already exists in contact list, it was not saved";
  private final String INFO_CONTACT_INVALID = "Sorry, invalid contact! Can't save.";
  private final String INFO_SESSION_CREATION_FAILED = "Failed to create sesssion. Delete contact and get new key bundle!";

  private final int CHAR_THRESHOLD_BRAVE = 500;
  private final int CHAR_THRESHOLD_STEGO = 500;

  public E2EEMenu(Context context) {
    messgaeContext = context;
  }

  CharSequence encryptMessage(final String unencryptedMessage, final SignalProtocolAddress signalProtocolAddress, Encoder encoder) throws IOException {
    checkMessageLengthForEncodingMethod(unencryptedMessage, encoder, false);
    final MessageFrame messageFrame = SignalProtocolMain.encryptMessage(unencryptedMessage, signalProtocolAddress);
    String json = JsonUtil.toJson(messageFrame);
    if (json == null) return null;
    return encode(json, encoder);
  }

  CharSequence decryptMessage(final MessageFrame messageFrame, final Contact sender) {
    CharSequence decryptedMessage = null;
    try {
      updateSessionWithNewSignedPreKeyIfNecessary(messageFrame, sender);

      decryptedMessage = SignalProtocolMain.decryptMessage(messageFrame, sender.getSignalProtocolAddress());
    } catch (InvalidMessageException | NoSessionException | ContactIncorrectException |
             MessageUnknownException |
             UntrustedIdentityException | DuplicateMessageException | InvalidVersionException |
             InvalidKeyIdException |
             LegacyMessageException | InvalidKeyException e) {
      Log.e(TAG, "Error: Decrypting message failed");
      e.printStackTrace();
    }
    return decryptedMessage;
  }

  public String encode(final String message, final Encoder encoder) throws IOException {
    String encodedMessage = null;
    if (encoder.equals(Encoder.STEGO))
      encodedMessage = StegoEncoder.encode(message, messgaeContext);
    if (encoder.equals(Encoder.BRAVE)) encodedMessage = BraveEncoder.encode(message);
    return encodedMessage;
  }

  private void updateSessionWithNewSignedPreKeyIfNecessary(MessageFrame messageFrame, Contact sender) {
    if (messageFrame.preKeyResponse != null && messageFrame.ciphertextMessage != null) {
      SignalProtocolMain.processPreKeyResponseMessage(messageFrame, sender.getSignalProtocolAddress());
    }
  }

  CharSequence getEncryptedMessageFromClipboard() {
    ClipboardManager clipboardManager =
        (ClipboardManager) messgaeContext.getSystemService(Context.CLIPBOARD_SERVICE);

    if (clipboardManager != null) {
      try {
        // listener for HTML text needed for using app with telegram
        if (clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) ||
            clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) {
          ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
          return HTMLFormatter.replaceHtmlCharacters(item.getText().toString());
        }
      } catch (Exception e) {
        e.printStackTrace();
        Log.e(TAG, "Error: Getting clipboard message!");
      }
    }
    return null;
  }

  void clearClipboard() {
    ClipboardManager clipboardManager =
        (ClipboardManager) messgaeContext.getSystemService(Context.CLIPBOARD_SERVICE);

    if (clipboardManager != null) {
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          clipboardManager.clearPrimaryClip();
          // debug Toast.makeText(mContext, "Clipboard deleted!", Toast.LENGTH_SHORT).show();
        } else {
          // support for older devices
          ClipData clipData = ClipData.newPlainText("", "");
          clipboardManager.setPrimaryClip(clipData);
          // debug: Toast.makeText(mContext, "Clipboard deleted!", Toast.LENGTH_SHORT).show();
        }
      } catch (Exception e) {
        e.printStackTrace();
        Log.e(TAG, "Error: Clearing clipboard message!");
      }
    }
  }

  public ArrayList<Contact> getContacts() {
    return SignalProtocolMain.getContactList();
  }

  public Contact createAndAddContactToContacts(final CharSequence firstName, final CharSequence lastName, final String signalProtocolAddressName, final int deviceId) {
    Contact contact = null;
    try {
      contact = SignalProtocolMain.addContact(firstName, lastName, signalProtocolAddressName, deviceId);
    } catch (ContactDuplicatedException e) {
      Toast.makeText(messgaeContext, INFO_CONTACT_ALREADY_EXISTS, Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    } catch (ContactIncorrectException e) {
      Toast.makeText(messgaeContext, INFO_CONTACT_INVALID, Toast.LENGTH_SHORT).show();
      e.printStackTrace();
    }
    return contact;
  }

  public boolean createSessionWithContact(Contact chosenContact, MessageFrame messageFrame, SignalProtocolAddress recipientProtocolAddress) {
    boolean successful = SignalProtocolMain.processPreKeyResponseMessage(messageFrame, recipientProtocolAddress);
    if (successful) {
      Toast.makeText(messgaeContext, "Session with " + chosenContact.getFirstName() + " " + chosenContact.getLastName() + " created", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(messgaeContext, INFO_SESSION_CREATION_FAILED, Toast.LENGTH_SHORT).show();
    }
    return successful;
  }

  public String getPreKeyResponseMessage() {
    final MessageFrame messageFrame = SignalProtocolMain.getPreKeyResponseMessage();
    return JsonUtil.toJson(messageFrame);
  }

  public Object getContactFromFrame(MessageFrame messageFrame) {
    return SignalProtocolMain.extractContactFromMessageFrame(messageFrame);
  }

  public MessageType getMessageType(MessageFrame messageFrame) {
    return SignalProtocolMain.getMessageType(messageFrame);
  }

  public void removeContact(Contact contact) {
    SignalProtocolMain.removeContactFromContactListAndProtocol(contact);
  }

  public List<MessageStorage> getUnencryptedMessages(Contact contact) throws ContactUnknownException {
    return SignalProtocolMain.getUnencryptedMessagesList(contact);
  }

  public String getAccountName() {
    return SignalProtocolMain.getNameOfAccount();
  }

  public Fingerprint getFingerprint(Contact contact) {
    return SignalProtocolMain.getFingerprint(contact);
  }

  public void verifyContact(Contact contact) throws ContactUnknownException {
    SignalProtocolMain.verifyContact(contact);
  }

  public String decodeMessage(String encodedMessage) throws IOException {
    if (EncodingHelper.encodedTextContainsInvisibleCharacters(encodedMessage)) {
      return StegoEncoder.decode(encodedMessage);
    } else {
      return BraveEncoder.decode(encodedMessage);
    }
  }

  public void checkMessageLengthForEncodingMethod(String message, Encoder encodingMethod, boolean isPreKeyResponse) throws CharacterOverflowException {
    if (message == null || encodingMethod == null) return;
    final int messageBytes = message.getBytes(StandardCharsets.UTF_8).length;
    if (isPreKeyResponse && messageBytes > CHAR_THRESHOLD_BRAVE)
      throw new CharacterOverflowException(String.format("The invite/update message is toooo long (%s characters used; only %s characters are permitted).", messageBytes, CHAR_THRESHOLD_BRAVE));
    if (encodingMethod.equals(Encoder.BRAVE) && messageBytes > CHAR_THRESHOLD_BRAVE) {
      throw new CharacterOverflowException(String.format("The brave message exceeds the character limit (%s characters used; only %s characters allowed).", messageBytes, CHAR_THRESHOLD_BRAVE));
    } else if (encodingMethod.equals(Encoder.STEGO) && messageBytes > CHAR_THRESHOLD_STEGO) {
      throw new CharacterOverflowException(String.format("Too many characters for stego message (%s characters, only %s characters allowed)", messageBytes, CHAR_THRESHOLD_STEGO));
    }
  }
}
