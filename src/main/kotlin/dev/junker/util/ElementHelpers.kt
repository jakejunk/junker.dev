package dev.junker.util

@JvmInline
value class ElementClass(val className: String) {
    val selector get() = ".$className"
}

@JvmInline
value class ElementId(val id: String) {
    val selector get() = "#$id"
}

fun String.asClass() = ElementClass(this)

fun String.asId() = ElementId(this)
