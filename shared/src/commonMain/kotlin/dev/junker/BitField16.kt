package dev.junker

import kotlin.jvm.JvmInline

private val ZERO = 0u.toUShort()

@JvmInline
value class BitField16(
    private val data: UShort = ZERO
) {
    fun getBit(position: Int): Boolean {
        return (data and withBitEnabled(position)) != ZERO
    }

    fun setBit(position: Int, value: Boolean): BitField16 {
        val newData = when (value) {
            true -> data or withBitEnabled(position)
            else -> data and withBitEnabled(position).inv()
        }

        return BitField16(newData)
    }

    fun setBits(other: BitField16): BitField16 {
        return BitField16(data or other.data)
    }

    fun clearBits(other: BitField16): BitField16 {
        return BitField16(data and other.data.inv())
    }

    private fun withBitEnabled(position: Int): UShort {
        return (1 shl position).toUShort()
    }
}
