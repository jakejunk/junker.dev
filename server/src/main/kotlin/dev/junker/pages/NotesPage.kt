package dev.junker.pages

import dev.junker.classSelector
import dev.junker.components.table.notesGrid
import dev.junker.markdown.MarkdownMetadata
import dev.junker.markdown.markdownDocument
import dev.junker.markdown.parseMetadata
import dev.junker.util.loadResourceText
import kotlinx.html.FlowContent
import kotlinx.html.h1

val notesHeader = "notes-header".classSelector()

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
            h1(classes = notesHeader.className) { +"Notes" }

            notesGrid(items = noteMetadata)
        }
    }

    data class FromFile(
        override val slug: String,
        override val title: String,
        override val description: String,
        override val content: FlowContent.() -> Unit
    ) : NotesPage
}

fun notePage(noteName: String): NotesPage? {
    val noteNameFq = "${NotesPage.ROOT_SLUG}/$noteName"
    val markdownText = loadResourceText("${noteNameFq}.md") ?: return null

    val (metadata, remainingMarkdown) = parseMetadata(noteNameFq, markdownText)
    val document = markdownDocument(metadata, remainingMarkdown)

    return NotesPage.FromFile(
        slug = noteNameFq,
        title = metadata.title ?: "untitled",
        description = metadata.description ?: "undefined",
        content = document.content
    )
}
