package dev.junker.maze

import dev.junker.BitField16
import dev.junker.maze.cell.MazeCell
import dev.junker.maze.cell.WallDirection
import kotlin.random.Random

class Maze private constructor(
    val seed: Int,
    val sideLength: Int,
    val cells: List<MazeCell>
) {
    fun Int.toPosition(): Position {
        return Position(this % sideLength, this / sideLength)
    }

    fun Position.toIndex(): Int? {
        val i = x + y * sideLength

        return when {
            i >= cells.size -> null
            else -> i
        }
    }

    companion object {
        fun simple(
            seed: Int,
            sideLength: Int
        ): Maze {
            if (sideLength < 2) {
                throw IllegalArgumentException("sideLength must be at least 2")
            }

            val mazeCells = MutableList(sideLength * sideLength) { MazeCell(BitField16(WallDirection.ALL)) }
            val maze = Maze(seed, sideLength, mazeCells)
            val uniqueRooms = ArrayList<MutableList<Position>>()
            val wallsToRemove = ArrayList<Wall>()

            for (y in 0..<sideLength)
            {
                for (x in 0..<sideLength)
                {
                    val p = Position(x, y)

                    uniqueRooms.add(MutableList(1) { p })

                    if (x + 1 < sideLength) {
                        wallsToRemove.add(Wall(p, p.plusX(1), WallDirection.EAST))
                    }

                    if (y + 1 < sideLength) {
                        wallsToRemove.add(Wall(p, p.plusY(1), WallDirection.SOUTH))
                    }
                }
            }

            val rng = Random(seed)

            while (wallsToRemove.isNotEmpty()) {
                val i = rng.nextInt(wallsToRemove.size)

                val wall = wallsToRemove.removeAt(i)

                val aRoomIndex = uniqueRooms.indexOfFirst { set -> set.contains(wall.aRoomPosition) }
                val bRoomIndex = uniqueRooms.indexOfFirst { set -> set.contains(wall.bRoomPosition) }

                if (aRoomIndex == bRoomIndex) {
                    continue
                }

                with(maze) {
                    val aCellIndex = wall.aRoomPosition.toIndex()!!
                    val bCellIndex = wall.bRoomPosition.toIndex()!!

                    mazeCells[aCellIndex] = mazeCells[aCellIndex].removeWall(wall.aWall)
                    mazeCells[bCellIndex] = mazeCells[bCellIndex].removeWall(wall.bWall)
                }

                val toMerge = uniqueRooms.removeAt(bRoomIndex)

                val targetIndex = when {
                    bRoomIndex < aRoomIndex -> aRoomIndex - 1
                    else -> aRoomIndex
                }

                uniqueRooms[targetIndex].addAll(toMerge)
            }

            return maze
        }
    }
}

private data class Wall(
    val aRoomPosition : Position,
    val bRoomPosition : Position,
    val aWall: WallDirection
) {
    val bWall: WallDirection
        get() = aWall.opposite()
}
