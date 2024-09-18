package com.richdear.privacykeyboard.inputmethod.signal_protocol.util

import android.util.Log
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.IntNode
import com.google.protobuf.ByteString
import com.richdear.privacykeyboard.inputmethod.signal_protocol.SenderKey
import com.richdear.privacykeyboard.inputmethod.signal_protocol.SignalProtocolMain
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.Contact
import com.richdear.privacykeyboard.inputmethod.signal_protocol.chat.MessageStorage
import com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions.InvalidResponseFormatException
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.Base64.decodeWithoutPadding
import com.richdear.privacykeyboard.inputmethod.signal_protocol.util.Base64.encodeBytesWithoutPadding
import org.signal.libsignal.protocol.IdentityKey
import org.signal.libsignal.protocol.IdentityKeyPair
import org.signal.libsignal.protocol.InvalidKeyException
import org.signal.libsignal.protocol.SignalProtocolAddress
import java.io.IOException

object JsonUtil {
    private val TAG: String = JsonUtil::class.java.simpleName

    private val objectMapper = ObjectMapper()

    init {
        val module = SimpleModule()
        module.addSerializer(IdentityKeyPair::class.java, IdentityKeyPairSerializer())
        module.addDeserializer(IdentityKeyPair::class.java, IdentityKeyPairDeserializer())
        module.addSerializer(IdentityKey::class.java, IdentityKeySerializer())
        module.addDeserializer(IdentityKey::class.java, IdentityKeyDeserializer())
        module.addSerializer(SignalProtocolAddress::class.java, SignalProtocolAddressSerializer())
        module.addDeserializer(
            SignalProtocolAddress::class.java,
            SignalProtocolAddressDeserializer()
        )
        module.addKeySerializer(SenderKey::class.java, SenderKeySerializer())
        module.addKeyDeserializer(SenderKey::class.java, SenderKeyDeserializer())
        objectMapper.registerModule(module)
        objectMapper.findAndRegisterModules() // for Instant type
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL) // ignore null values
        // objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // for pretty json
    }

    @JvmStatic
    fun toJson(`object`: Any?): String? {
        try {
            return objectMapper.writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            if (!SignalProtocolMain.testIsRunning) Log.w(TAG, e)
            e.printStackTrace()
            return null
        }
    }

    fun toJsonByteString(`object`: Any?): ByteString {
        return ByteString.copyFrom(
            toJson(`object`)!!
                .toByteArray()
        )
    }

    @JvmStatic
    @Throws(IOException::class)
    fun <T> fromJson(json: String?, clazz: Class<T>?): T {
        return objectMapper.readValue(json, clazz)
    }

    @Throws(IOException::class)
    fun <T> fromJson(json: String?, typeRef: TypeReference<T>?): T {
        return objectMapper.readValue(json, typeRef)
    }

    @Throws(InvalidResponseFormatException::class)
    fun <T> fromJsonResponse(json: String?, typeRef: TypeReference<T>?): T {
        try {
            return fromJson(json, typeRef)
        } catch (e: IOException) {
            throw InvalidResponseFormatException("Unable to parse entity", e)
        }
    }

    @Throws(InvalidResponseFormatException::class)
    fun <T> fromJsonResponse(body: String?, clazz: Class<T>?): T {
        try {
            return fromJson(body, clazz)
        } catch (e: IOException) {
            throw InvalidResponseFormatException("Unable to parse entity", e)
        }
    }

    fun convertContactsList(classFromSharedPreferences: ArrayList<Contact>): ArrayList<Contact> {
        return objectMapper.convertValue(classFromSharedPreferences, object : TypeReference<ArrayList<Contact>>() {})
    }

    fun convertUnencryptedMessagesList(classFromSharedPreferences: ArrayList<MessageStorage>): ArrayList<MessageStorage> {
        return objectMapper.convertValue(classFromSharedPreferences, object : TypeReference<ArrayList<MessageStorage>>() {})
    }

    class IdentityKeySerializer : JsonSerializer<IdentityKey>() {
        @Throws(IOException::class)
        override fun serialize(
            value: IdentityKey,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            Log.d(TAG, "IdentityKeySerializer used")
            gen.writeStartObject()
            gen.writeStringField(
                "publicKey",
                encodeBytesWithoutPadding(value.publicKey.serialize())
            )
            gen.writeEndObject()
        }
    }

    class IdentityKeyDeserializer : JsonDeserializer<IdentityKey>() {
        @Throws(IOException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): IdentityKey {
            try {
                Log.d(TAG, "IdentityKeyDeserializer used")
                val node = p.codec.readTree<JsonNode>(p)
                return IdentityKey(
                    decodeWithoutPadding(
                        node["publicKey"].asText()
                    ), 0
                )
            } catch (e: InvalidKeyException) {
                throw IOException(e)
            }
        }
    }

    class IdentityKeyPairSerializer : JsonSerializer<IdentityKeyPair>() {
        @Throws(IOException::class)
        override fun serialize(
            value: IdentityKeyPair,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            Log.d(TAG, "IdentityKeyPairSerializer used")
            gen.writeString(encodeBytesWithoutPadding(value.serialize()))
        }
    }

    class IdentityKeyPairDeserializer : JsonDeserializer<IdentityKeyPair>() {
        @Throws(IOException::class)
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): IdentityKeyPair {
            Log.d(TAG, "IdentityKeyPairDeserializer used")
            return IdentityKeyPair(decodeWithoutPadding(p.valueAsString))
        }
    }

    class SignalProtocolAddressSerializer : JsonSerializer<SignalProtocolAddress>() {
        @Throws(IOException::class)
        override fun serialize(
            value: SignalProtocolAddress,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            Log.d(TAG, "SignalProtocolAddressKeySerializer used")
            gen.writeStartObject()
            gen.writeStringField("name", value.name)
            gen.writeNumberField("deviceId", value.deviceId)
            gen.writeEndObject()
        }
    }

    class SignalProtocolAddressDeserializer : JsonDeserializer<SignalProtocolAddress>() {
        @Throws(IOException::class)
        override fun deserialize(
            p: JsonParser,
            ctxt: DeserializationContext
        ): SignalProtocolAddress {
            Log.d(TAG, "SignalProtocolAddressDeserializer used")
            val node = p.codec.readTree<JsonNode>(p)
            val name = node["name"].asText()
            val deviceId = (node["deviceId"] as IntNode).numberValue() as Int
            return SignalProtocolAddress(name, deviceId)
        }
    }

    class SenderKeyDeserializer : KeyDeserializer() {
        override fun deserializeKey(key: String, ctxt: DeserializationContext): SenderKey {
            Log.d(TAG, "SenderKeyDeserializer used")
            val allThree = key.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val name = allThree[0]
            val deviceId = allThree[1].toInt()
            val distributionId = allThree[2]
            return SenderKey(name, deviceId, distributionId)
        }
    }

    class SenderKeySerializer : JsonSerializer<SenderKey>() {
        @Throws(IOException::class)
        override fun serialize(
            value: SenderKey,
            gen: JsonGenerator,
            serializers: SerializerProvider
        ) {
            Log.d(TAG, "SenderKeySerializer used")
            gen.writeStartObject()
            gen.writeStringField("name", value.signalProtocolAddressName)
            gen.writeNumberField("deviceId", value.deviceId)
            gen.writeStringField("distributionId", value.distributionId)
            gen.writeEndObject()
        }
    }
}
