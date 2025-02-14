package dev.junker.pages

import dev.junker.markdown.MarkdownMetadata
import dev.junker.markdown.markdownDocument
import dev.junker.markdown.parseMetadata
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.h1

sealed interface NotesPage : Page.Content {
    companion object {
        const val ROOT_SLUG = "/notes"
    }

    class Index(
        noteMetadata: List<MarkdownMetadata>
    ) : NotesPage {
        override val slug = ROOT_SLUG
        override val title = "Notes"
        override val description = "Don't mind the mess."
        override val content: FlowContent.() -> Unit = {
            h1 { +"Notes - Work In Progress" }
            for (metadata in noteMetadata) {
                article {
                    val title = metadata.title ?: "untitled"

                    a(href = metadata.slug) { +title }
                }
            }
        }
    }

    data class FromFile(
        override val slug: String,
        override val title: String,
        override val description: String,
        override val content: FlowContent.() -> Unit
    ) : NotesPage
}

fun notesPage(slug: String, markdownText: String): NotesPage {
    val (metadata, remainingMarkdown) = parseMetadata(slug, markdownText)
    val document = markdownDocument(metadata, remainingMarkdown)

    return NotesPage.FromFile(
        slug = slug,
        title = metadata.title ?: "untitled",
        description = metadata.description ?: "undefined",
        content = document.content
    )
}
