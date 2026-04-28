package dev.junker.splice

sealed interface SpliceOperator {
    val direction: Direction

    data class Add(
        override val direction: Direction
    ) : SpliceOperator

    data class Subtract(
        override val direction: Direction
    ) : SpliceOperator

    data class Multiply(
        override val direction: Direction
    ) : SpliceOperator

    data class Divide(
        override val direction: Direction
    ) : SpliceOperator
}
