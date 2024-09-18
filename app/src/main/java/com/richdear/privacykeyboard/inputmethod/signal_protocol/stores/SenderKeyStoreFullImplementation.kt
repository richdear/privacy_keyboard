package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import com.richdear.privacykeyboard.inputmethod.signal_protocol.SenderKey
import org.signal.libsignal.protocol.InvalidMessageException
import org.signal.libsignal.protocol.SignalProtocolAddress
import org.signal.libsignal.protocol.groups.state.SenderKeyRecord
import org.signal.libsignal.protocol.groups.state.SenderKeyStore
import java.util.UUID

class SenderKeyStoreFullImplementation : SenderKeyStore {
    @JsonProperty
    private val store: MutableMap<SenderKey, SenderKeyRecord> =
        HashMap() //private final Map<Pair<SignalProtocolAddress, UUID>, SenderKeyRecord> store = new HashMap<>();

    override fun storeSenderKey(
        sender: SignalProtocolAddress,
        distributionId: UUID,
        record: SenderKeyRecord
    ) {
        Log.d(
            TAG,
            "Storing SenderKeyRecord with address: $sender and distributionId: $distributionId and record: $record"
        )
        store[SenderKey(sender.name, sender.deviceId, distributionId.toString())] = record
    }

    override fun loadSenderKey(
        sender: SignalProtocolAddress,
        distributionId: UUID
    ): SenderKeyRecord? {
        Log.d(
            TAG,
            "Loading SenderKeyRecord with address: $sender and distributionId: $distributionId"
        )

        try {
            val record = store[SenderKey(sender.name, sender.deviceId, distributionId.toString())]

            return if (record == null) {
                null
            } else {
                SenderKeyRecord(record.serialize())
            }
        } catch (e: InvalidMessageException) {
            throw AssertionError(e)
        }
    }

    companion object {
        val TAG: String = SenderKeyStoreFullImplementation::class.java.simpleName
    }
}
