package dev.junker.components.pages

import dev.junker.markdown.markdownDocument
import dev.junker.util.allResources
import kotlinx.html.FlowContent
import kotlinx.html.a
import kotlinx.html.article
import kotlinx.html.h1

sealed interface NotesPage : Page.Content {
    companion object {
        const val ROOT_SLUG = "/notes"
    }

    class Index(
        noteNames: List<String>
    ) : NotesPage {
        override val slug = ROOT_SLUG
        override val title = "Notes - ${HomePage.title}"
        override val description = "Don't mind the mess."
        override val content: FlowContent.() -> Unit = {
            h1 { +"Notes - Work In Progress" }
            for (name in noteNames) {
                article {
                    a(href = "$ROOT_SLUG/$name") { +name }
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

fun allNotes(): Sequence<String> {
    val notes = allResources("/notes")
    return notes
}

fun notesPage(noteNameFq: String, markdownText: String): NotesPage {
    val document = markdownDocument(markdownText)

    return NotesPage.FromFile(
        slug = noteNameFq,
        title = document.title ?: "untitled",
        description = document.description ?: "undefined",
        content = document.build
    )
}
