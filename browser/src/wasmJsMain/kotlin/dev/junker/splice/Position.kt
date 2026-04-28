package dev.junker.splice

data class Position(
    val x: Int,
    val y: Int
) {
    fun plusX(x: Int): Position {
        return Position(this.x + x, this.y)
    }

    fun plusY(y: Int): Position {
        return Position(this.x + y, this.y)
    }

    infix fun upThrough(end: Position): Range {
        return Range(this, end)
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}

data class Range(
    val start: Position,
    val end: Position
) {
    fun contains(position: Position): Boolean {
        return position.x in start.x..end.x
            && position.y in start.y..end.y
    }

    override fun toString(): String {
        return "$start to $end"
    }
}
