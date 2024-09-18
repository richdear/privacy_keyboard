package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

import android.util.Log
import com.fasterxml.jackson.annotation.JsonProperty
import com.richdear.privacykeyboard.inputmethod.signal_protocol.PreKeyWithStatus
import org.signal.libsignal.protocol.InvalidKeyIdException
import org.signal.libsignal.protocol.InvalidMessageException
import org.signal.libsignal.protocol.state.PreKeyRecord
import org.signal.libsignal.protocol.state.PreKeyStore
import java.util.Objects

class PreKeyStoreFullImplementation : PreKeyStore {
    @JsonProperty
    private val store: MutableMap<Int, PreKeyWithStatus> = HashMap()

    @Throws(InvalidKeyIdException::class)
    override fun loadPreKey(preKeyId: Int): PreKeyRecord {
        Log.d(TAG, "Loading PreKeyRecord with id: $preKeyId")
        try {
            if (!store.containsKey(preKeyId)) {
                throw InvalidKeyIdException("No such PreKeyRecord! (id = $preKeyId)")
            }

            store[preKeyId] = PreKeyWithStatus(store[preKeyId]?.serializedPreKeyRecord ?: throw InvalidKeyIdException("No such PreKeyRecord! (id = $preKeyId)"), true)

            Log.d(TAG, "Setting PreKeyRecord with id $preKeyId to used")

            return PreKeyRecord(store[preKeyId]?.serializedPreKeyRecord ?: throw InvalidKeyIdException("No such PreKeyRecord! (id = $preKeyId)"))
        } catch (e: InvalidMessageException) {
            throw AssertionError(e)
        }
    }

    override fun storePreKey(preKeyId: Int, record: PreKeyRecord) {
        Log.d(TAG, "Storing PreKeyRecord with id: $preKeyId")
        store[preKeyId] = PreKeyWithStatus(record.serialize(), false)
    }

    override fun containsPreKey(preKeyId: Int): Boolean {
        return store.containsKey(preKeyId)
    }

    override fun removePreKey(preKeyId: Int) {
        Log.d(TAG, "Removing PreKeyRecord with id: $preKeyId")
        store.remove(preKeyId)
    }

    fun removeAllPreKeys() {
        Log.d(TAG, "Removing all PreKeyRecords")
        store.clear()
    }

    val availablePreKeys: Int
        // Count of currently available (eg. unused) prekeys
        get() = store.entries.stream()
            .filter { p: Map.Entry<Int, PreKeyWithStatus> -> !p.value.isUsed }
            .count().toInt()

    fun checkPreKeyAvailable(preKeyId: Int): Boolean? {
        return if (store.containsKey(preKeyId)) Objects.requireNonNull(
            store[preKeyId]
        )?.isUsed else null
    }

    val size: Int
        get() = store.size

    companion object {
        val TAG: String = PreKeyStoreFullImplementation::class.java.simpleName
    }
}

