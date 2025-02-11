package dev.junker.util

import java.io.File

// TODO: Is there a more efficient way to do this?
fun loadResourceText(resourceName: String): String? {
    return object {}.javaClass.getResourceAsStream(resourceName)
        ?.bufferedReader()
        ?.use { it.readText() }
        ?.replace(Regex("(\r\n|\r)"), "\n")
}

fun allResources(parentDirName: String): Sequence<String> {
    val parentDir = object {}.javaClass.getResource(parentDirName)?.file
        ?.let { File(it) }

    return when (parentDir) {
        null -> emptySequence()
        else -> allFilesInDirectory(parentDir)
    }
}

private fun allFilesInDirectory(parentDir: File): Sequence<String> {
    return parentDir.walk().mapNotNull { f ->
        when {
            f.isFile -> f.nameWithoutExtension
            else -> null
        }
    }
}
