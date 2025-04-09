package dev.junker.components.grid

import dev.junker.classSelector
import dev.junker.markdown.MarkdownMetadata
import kotlinx.html.*

val notesGrid = "notes-grid".classSelector()
val gridCard = "grid-card".classSelector()

fun FlowContent.notesGrid(
    items: List<MarkdownMetadata>
) {
    div(notesGrid.className) {
        items.forEach { item ->
            a(href = item.slug, classes = gridCard.className) {
                article {
                    header {
                        (item.modifiedDate ?: item.creationDate)?.also { t ->
                            time {
                                dateTime = t
                                +t
                            }
                        }
                        h2 { +(item.title ?: "untitled") }
                    }
                    p {
                        +(item.description ?: "No description.")
                    }
                }
            }
        }
    }
}
