package dev.junker.splice

sealed interface SpliceCell {
    val value: UByte

    data object Null : SpliceCell {
        override val value: UByte = 0u
    }

    data class Filled(
        override val value: UByte
    ) : SpliceCell {
        fun isJumpCell() = value == UByte.MAX_VALUE
    }
}

fun SpliceOperator.perform(
    lhs: SpliceCell,
    rhs: SpliceCell
): SpliceCell.Filled {
    val updatedValue = when (this) {
        is SpliceOperator.Add -> lhs.value + rhs.value
        is SpliceOperator.Subtract -> lhs.value - rhs.value
        is SpliceOperator.Multiply -> lhs.value * rhs.value
        is SpliceOperator.Divide -> lhs.value / rhs.value
    }

    return SpliceCell.Filled(updatedValue.toUByte())
}
