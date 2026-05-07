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

data class PlacedOperator(
    private val position: Position,
    val operator: SpliceOperator
) {
    val lhsPosition: Position
        get() = position

    val rhsPosition: Position = when (operator.direction) {
        Direction.VERTICAL -> position.plusY(1)
        Direction.HORIZONTAL -> position.plusX(1)
    }

    val resultPosition: Position = when (operator.direction) {
        Direction.VERTICAL -> position.plusY(2)
        Direction.HORIZONTAL -> position.plusX(2)
    }
}

infix fun PlacedOperator.contains(p: Position): Boolean {
    return p.x in lhsPosition.x..resultPosition.x
        && p.y in lhsPosition.y..resultPosition.y
}

infix fun PlacedOperator.overlaps(other: PlacedOperator): Boolean {
    return lhsPosition.x <= other.resultPosition.x
        && resultPosition.x >= other.lhsPosition.x
        && lhsPosition.y <= other.resultPosition.y
        && resultPosition.y >= other.lhsPosition.y
}

fun parseSpliceOperator(value: String, direction: Direction): SpliceOperator? {
    return when (value) {
        "+" -> SpliceOperator.Add(direction)
        "-" -> SpliceOperator.Subtract(direction)
        "×" -> SpliceOperator.Multiply(direction)
        "÷" -> SpliceOperator.Divide(direction)
        else -> null
    }
}
