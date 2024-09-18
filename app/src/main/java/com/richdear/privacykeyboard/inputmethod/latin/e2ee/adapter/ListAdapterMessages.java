package com.richdear.privacykeyboard.inputmethod.latin.e2ee.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.richdear.privacykeyboard.R;
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.MessageStorage;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListAdapterMessages extends ArrayAdapter<Object> {

  private final ArrayList<Object> messageListStorageMessages;
  private final String accountName;
  private static final String PATTERN_FORMAT = "dd.MM.yyyy HH:mm:ss";

  public ListAdapterMessages(
      Context context,
      int resource,
      ArrayList<Object> listStorageMessages,
      String accountName) {
    super(context, resource, listStorageMessages);
    this.messageListStorageMessages = listStorageMessages;
    this.accountName = accountName;
  }

  public View getView(final int position, View convertView, ViewGroup parent) {

    final MessageStorage message = (MessageStorage) getItem(position);

    if (convertView == null) {
      LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
      convertView = layoutInflater.inflate(R.layout.e2ee_message_element_view, null, false);
    }

    final TextView ownMessageTextView = convertView.findViewById(R.id.e2ee_own_messages_text_view_element);
    final TextView ownMessageTimestampTextView = convertView.findViewById(R.id.e2ee_own_messages_timestamp_text_view_element);

    final TextView othersMessageTextView = convertView.findViewById(R.id.e2ee_others_messages_text_view_element);
    final TextView othersMessageTimestampTextView = convertView.findViewById(R.id.e2ee_others_messages_timestamp_text_view_element);

    if (message != null && accountName != null && accountName.equals(message.senderUUID)) {
      ownMessageTextView.setText(message.unencryptedMessage);
      ownMessageTextView.setVisibility(View.VISIBLE);

      ownMessageTimestampTextView.setText(formatInstant(message.timestamp));
      ownMessageTimestampTextView.setVisibility(View.VISIBLE);

      othersMessageTimestampTextView.setVisibility(View.GONE);
      othersMessageTextView.setVisibility(View.GONE);
    } else if (message != null && accountName != null && accountName.equals(message.recipientUUID)) {
      othersMessageTextView.setText(message.unencryptedMessage);
      othersMessageTextView.setVisibility(View.VISIBLE);

      othersMessageTimestampTextView.setText(formatInstant(message.timestamp));
      othersMessageTimestampTextView.setVisibility(View.VISIBLE);

      ownMessageTimestampTextView.setVisibility(View.GONE);
      ownMessageTextView.setVisibility(View.GONE);
    }
    return convertView;
  }

  @Override
  public Object getItem(int position) {
    MessageStorage message = (MessageStorage) messageListStorageMessages.get(position);
    return message;
  }

  private String formatInstant(Instant timestamp) {
    return DateTimeFormatter.ofPattern(PATTERN_FORMAT)
        .withZone(ZoneId.systemDefault()).format(timestamp);
  }
}
