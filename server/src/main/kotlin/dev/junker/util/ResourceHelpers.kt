package dev.junker.util

// TODO: Is there a more efficient way to do this?
fun loadResourceText(resourceName: String): String? {
    return object {}.javaClass.getResourceAsStream(resourceName)
        ?.bufferedReader()
        ?.use { it.readText() }
        ?.replace(Regex("(\r\n|\r)"), "\n")
}
