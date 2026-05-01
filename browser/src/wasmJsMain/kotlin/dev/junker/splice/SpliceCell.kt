package dev.junker.splice

data class SpliceCell(
    val value: UByte
) {
    fun isNullCell() = value == UByte.MIN_VALUE

    fun isJumpCell() = value == UByte.MAX_VALUE
}

fun SpliceOperator.perform(
    lhs: SpliceCell,
    rhs: SpliceCell
): SpliceCell {
    val updatedValue = when (this) {
        is SpliceOperator.Add -> lhs.value + rhs.value
        is SpliceOperator.Subtract -> lhs.value - rhs.value
        is SpliceOperator.Multiply -> lhs.value * rhs.value
        is SpliceOperator.Divide -> lhs.value / rhs.value
    }

    return SpliceCell(updatedValue.toUByte())
}
