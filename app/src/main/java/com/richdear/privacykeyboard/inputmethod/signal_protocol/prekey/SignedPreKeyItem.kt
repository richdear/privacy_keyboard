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
import org.signal.libsignal.protocol.ecc.ECPublicKey
import java.io.IOException

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
class SignedPreKeyItem : PreKeyItem {
    @JsonProperty
    @JsonSerialize(using = ByteArraySerializer::class)
    @JsonDeserialize(using = ByteArrayDeserializer::class)
    var signature: ByteArray? = null

    constructor()

    constructor(keyId: Int, publicKey: ECPublicKey?, signature: ByteArray) : super(
        keyId,
        publicKey
    ) {
        this.signature = signature
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val that = o as SignedPreKeyItem
        return signature.contentEquals(that.signature)
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + signature.contentHashCode()
        return result
    }

    private class ByteArraySerializer : JsonSerializer<ByteArray?>() {
        @Throws(IOException::class)
        override fun serialize(
            value: ByteArray?,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            gen.writeString(Base64.encodeBytesWithoutPadding(value!!))
        }
    }

    private class ByteArrayDeserializer : JsonDeserializer<ByteArray>() {
        @Throws(IOException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ByteArray {
            return Base64.decodeWithoutPadding(p.valueAsString)!!
        }
    }
}
