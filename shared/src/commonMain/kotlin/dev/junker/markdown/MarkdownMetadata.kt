package dev.junker.markdown

data class MarkdownMetadata(
    val slug: String,
    val title: String? = null,
    val description: String? = null,
    val creationDate: String? = null,
    val modifiedDate: String? = null
)

fun parseMetadata(slug: String, markdown: String): Pair<MarkdownMetadata, String> {
    val metadataRegex = Regex("^---\\s*\\n([\\s\\S]*?)\\n---\\s*\\n")
    val match = metadataRegex.find(markdown)

    return if (match != null) {
        val metadataBlock = match.groupValues[1]
        val remainingMarkdown = markdown.removeRange(match.range)
        val metadata = metadataBlock
            .lines()
            .mapNotNull(::parseMetadataLine)
            .toMap()

        MarkdownMetadata(
            slug = slug,
            title = metadata["title"],
            description = metadata["description"],
            creationDate = metadata["creationDate"],
            modifiedDate = metadata["modifiedDate"]
        ) to remainingMarkdown
    } else {
        MarkdownMetadata(
            slug = slug
        ) to markdown
    }
}

private fun parseMetadataLine(line: String): Pair<String, String>? {
    val parts = line.split(":", limit = 2).map { it.trim() }

    return when (parts.size) {
        2 -> parts[0] to parts[1]
        else -> null
    }
}
