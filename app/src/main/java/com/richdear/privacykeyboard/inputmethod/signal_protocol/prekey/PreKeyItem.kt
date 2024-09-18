package com.richdear.privacykeyboard.inputmethod.signal_protocol.prekey

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.Base64
import org.signal.libsignal.protocol.InvalidKeyException
import org.signal.libsignal.protocol.ecc.Curve
import org.signal.libsignal.protocol.ecc.ECPublicKey
import java.io.IOException
import java.util.Objects

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
open class PreKeyItem {
    @JsonProperty
    var keyId: Int = 0
        private set

    @JsonProperty
    @JsonSerialize(using = ECPublicKeySerializer::class)
    @JsonDeserialize(using = ECPublicKeyDeserializer::class)
    var publicKey: ECPublicKey? = null
        private set

    constructor()

    constructor(keyId: Int, publicKey: ECPublicKey?) {
        this.keyId = keyId
        this.publicKey = publicKey
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PreKeyItem
        return keyId == that.keyId && publicKey == that.publicKey
    }

    override fun hashCode(): Int {
        return Objects.hash(keyId, publicKey)
    }

    private class ECPublicKeySerializer : JsonSerializer<ECPublicKey>() {
        @Throws(IOException::class)
        override fun serialize(
            value: ECPublicKey,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            gen.writeString(Base64.encodeBytesWithoutPadding(value.serialize()))
        }
    }

    private class ECPublicKeyDeserializer : JsonDeserializer<ECPublicKey>() {
        @Throws(IOException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ECPublicKey {
            try {
                return Curve.decodePoint(Base64.decodeWithoutPadding(p.valueAsString), 0)
            } catch (e: InvalidKeyException) {
                throw IOException(e)
            }
        }
    }
}
