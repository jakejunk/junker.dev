package dev.junker.util

@JvmInline
value class ElementClass(val className: String) {
    val css get() = ".$className"
}

fun String.asClass() = ElementClass(this)
