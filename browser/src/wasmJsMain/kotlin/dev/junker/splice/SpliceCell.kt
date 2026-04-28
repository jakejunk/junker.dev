package dev.junker.splice

sealed interface SpliceCell {
    data object Null : SpliceCell

    data object Jump : SpliceCell

    data object Empty : SpliceCell

    data class Filled(val value: UByte) : SpliceCell
}
