package dev.junker.maze.cell

import dev.junker.BitField16

enum class WallDirection(val index: UShort) {
    NORTH(0u),
    EAST(1u),
    SOUTH(2u),
    WEST(3u);

    fun opposite(): WallDirection {
        return when (this) {
            NORTH -> SOUTH
            EAST -> WEST
            SOUTH -> NORTH
            WEST -> EAST
        }
    }
}

value class MazeCell(
    val value: BitField16 = BitField16(0u),
) {
    fun hasWall(wall: WallDirection): Boolean {
        return value.hasBit(wall.index.toInt())
    }

    fun addWall(wall: WallDirection): MazeCell {
        return MazeCell(value.setBit(wall.index.toInt(), true))
    }

    fun removeWall(wall: WallDirection): MazeCell {
        return MazeCell(value.setBit(wall.index.toInt(), false))
    }

    companion object {
        val ALL_FLAGS = MazeCell()
            .addWall(WallDirection.NORTH)
            .addWall(WallDirection.EAST)
            .addWall(WallDirection.SOUTH)
            .addWall(WallDirection.WEST)
    }
}
