package dev.junker.splice

sealed interface SpliceOperator {
    val direction: Direction

    data class Add(
        override val direction: Direction
    ) : SpliceOperator {
        override fun toString() = "+"
    }

    data class Subtract(
        override val direction: Direction
    ) : SpliceOperator {
        override fun toString() = "-"
    }

    data class Multiply(
        override val direction: Direction
    ) : SpliceOperator {
        override fun toString() = "×"
    }

    data class Divide(
        override val direction: Direction
    ) : SpliceOperator {
        override fun toString() = "÷"
    }
}

fun parseSpliceOperator(value: String): SpliceOperator? {
    return when (value) {
        "+_" -> SpliceOperator.Add(Direction.HORIZONTAL)
        "+|" -> SpliceOperator.Add(Direction.VERTICAL)
        "-_" -> SpliceOperator.Subtract(Direction.HORIZONTAL)
        "-|" -> SpliceOperator.Subtract(Direction.VERTICAL)
        "*_" -> SpliceOperator.Multiply(Direction.HORIZONTAL)
        "*|" -> SpliceOperator.Multiply(Direction.VERTICAL)
        "/_" -> SpliceOperator.Divide(Direction.HORIZONTAL)
        "/|" -> SpliceOperator.Divide(Direction.VERTICAL)
        else -> null
    }
}
