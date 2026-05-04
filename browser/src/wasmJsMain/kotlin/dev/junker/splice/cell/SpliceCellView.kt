package dev.junker.splice.cell

import dev.junker.*
import dev.junker.splice.Direction
import dev.junker.splice.SpliceOperator
import kotlinx.html.TagConsumer
import kotlinx.html.js.div
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

class SpliceCellView private constructor(
    val index: Int,
    val root: HTMLElement
) {
    var onCellSelected: ((SpliceCellView) -> Unit)? = null

    var value: UByte?
        get() = root.getAttribute(VALUE_ATTRIBUTE)
            ?.toUByteOrNull()
        set(value) = when (value) {
            null -> root.removeAttribute(VALUE_ATTRIBUTE)
            else -> {
                val formatted = value
                    .toString(16)
                    .padStart(2, '0')
                    .uppercase()

                root.setAttribute(VALUE_ATTRIBUTE, formatted)
            }
        }

    var selected: Boolean
        get() = root.classList.contains(spliceSelected.className)
        set(value) = when (value) {
            true -> root.classList.add(spliceSelected.className)
            false -> root.classList.remove(spliceSelected.className)
        }

    init {
        root.onpointerdown = { onCellSelected?.invoke(this) }
    }

    fun mark(
        operator: SpliceOperator,
        role: String
    ) {
        val classToggle = getClass(role, operator.direction)

        classToggle?.also {
            root.classList.add(it.className)

            when (operator.direction) {
                Direction.HORIZONTAL -> root.setAttribute("data-operator-h", operator.toString())
                Direction.VERTICAL -> root.setAttribute("data-operator-v", operator.toString())
            }
        }
    }

    fun clear(
        role: String,
        direction: Direction
    ) {
        val classToggle = getClass(role, direction)

        classToggle?.also {
            root.classList.remove(it.className)

            when (direction) {
                Direction.HORIZONTAL -> root.removeAttribute("data-operator-h")
                Direction.VERTICAL -> root.removeAttribute("data-operator-v")
            }
        }
    }

    private fun getClass(role: String, direction: Direction): Selector.Class? {
        return when (role) {
            "lhs" -> when (direction) {
                Direction.HORIZONTAL -> spliceLhsHorizontal
                Direction.VERTICAL -> spliceLhsVertical
            }
            "rhs" -> when (direction) {
                Direction.HORIZONTAL -> spliceRhsHorizontal
                Direction.VERTICAL -> spliceRhsVertical
            }
            "result" -> when (direction) {
                Direction.HORIZONTAL -> spliceResultHorizontal
                Direction.VERTICAL -> spliceResultVertical
            }
            else -> null
        }
    }

    companion object {
        const val VALUE_ATTRIBUTE = "data-value"

        fun TagConsumer<Element>.spliceCellView(index: Int): SpliceCellView {
            val root = div(classes = spliceCell.className)

            return SpliceCellView(index, root)
        }
    }
}
