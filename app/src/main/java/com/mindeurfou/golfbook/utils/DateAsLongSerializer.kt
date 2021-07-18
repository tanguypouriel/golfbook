package com.mindeurfou.golfbook.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

object DateAsLongSerializer : KSerializer<LocalDate> {
        override fun deserialize(decoder: Decoder): LocalDate =
            LocalDate.ofEpochDay(decoder.decodeLong())

        override fun serialize(encoder: Encoder, value: LocalDate) =
            encoder.encodeLong(value.toEpochDay())

        override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)
}
