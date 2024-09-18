package com.richdear.privacykeyboard.inputmethod.signal_protocol.stores

import com.fasterxml.jackson.annotation.JsonProperty
import com.richdear.privacykeyboard.inputmethod.signal_protocol.Session
import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.InvalidMessageException
import org.signal.libsignal.protocol.NoSessionException
import org.signal.libsignal.protocol.SignalProtocolAddress
import org.signal.libsignal.protocol.state.SessionRecord
import org.signal.libsignal.protocol.state.SessionStore
import java.util.LinkedList

class SessionStoreFullImplementation : SessionStore {
    @JsonProperty
    private var sessions: MutableList<Session> = ArrayList()

    @Synchronized
    override fun loadSession(remoteAddress: SignalProtocolAddress): SessionRecord {
        try {
            return if (containsSession(remoteAddress)) {
                SessionRecord(sessions.stream()
                    .filter { s: Session -> s.signalProtocolAddress == remoteAddress }
                    .findFirst()
                    .get().serializedSessionRecord)
            } else {
                SessionRecord()
            }
        } catch (e: InvalidMessageException) {
            throw AssertionError(e)
        }
    }

    @Synchronized
    @Throws(NoSessionException::class)
    override fun loadExistingSessions(addresses: List<SignalProtocolAddress>): List<SessionRecord> {
        val resultSessions: MutableList<SessionRecord> = LinkedList()
        for (remoteAddress in addresses) {
            val serialized = sessions.stream()
                .filter { s: Session -> s.signalProtocolAddress == remoteAddress }
                .findFirst()
                .get().serializedSessionRecord
            if (serialized == null) {
                throw NoSessionException("no session for $remoteAddress")
            }
            try {
                resultSessions.add(SessionRecord(serialized))
            } catch (e: InvalidMessageException) {
                throw AssertionError(e)
            }
        }
        return resultSessions
    }

    @Synchronized
    override fun getSubDeviceSessions(name: String): List<Int> {
        val deviceIds: MutableList<Int> = LinkedList()

        for (session in sessions) {
            val address = session.signalProtocolAddress
            if (address?.name == name &&
                address.deviceId != 1
            ) {
                deviceIds.add(address.deviceId)
            }
        }

        return deviceIds
    }

    @Synchronized
    override fun storeSession(address: SignalProtocolAddress, record: SessionRecord) {
        deleteSession(address)
        sessions.add(Session(address, record.serialize()))
    }

    @Synchronized
    override fun containsSession(address: SignalProtocolAddress): Boolean {
        for (session in sessions) {
            if (session.signalProtocolAddress?.name == address.name &&
                session.signalProtocolAddress?.deviceId == address.deviceId
            ) return true
        }
        return false
    }

    @Synchronized
    override fun deleteSession(address: SignalProtocolAddress) {
        val alteredSessionList: MutableList<Session> = ArrayList(sessions)
        for (session in sessions) {
            if (session.signalProtocolAddress?.name == address.name &&
                session.signalProtocolAddress?.deviceId == address.deviceId
            ) {
                alteredSessionList.remove(session)
            }
        }
        sessions = alteredSessionList
    }

    @Synchronized
    override fun deleteAllSessions(name: String) {
        val alteredSessionList: MutableList<Session> = ArrayList(sessions)
        for (session in sessions) {
            if (session.signalProtocolAddress?.name == name) {
                alteredSessionList.remove(session)
            }
        }
        sessions = alteredSessionList
    }

    val size: Int
        get() = sessions.size

    @Synchronized
    fun getPublicKeyFromSession(remoteAddress: SignalProtocolAddress): IdentityKey? {
        try {
            if (containsSession(remoteAddress)) {
                val record = SessionRecord(sessions.stream()
                    .filter { s: Session -> s.signalProtocolAddress == remoteAddress }
                    .findFirst()
                    .get().serializedSessionRecord)

                return IdentityKey(record.remoteIdentityKey.publicKey)
            } else {
                return null
            }
        } catch (e: InvalidMessageException) {
            throw AssertionError(e)
        }
    }
}
