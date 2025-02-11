package dev.junker

import dev.junker.components.pages.*
import dev.junker.components.site
import dev.junker.components.siteStyles
import dev.junker.util.loadResourceText
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CssBuilder

const val stylesRoute = "/styles.css"

fun Routing.routes() {
    staticResources("/", "/static/favicon")
    staticResources("/assets", "/static")

    get(stylesRoute) {
        call.respondCss {
            siteStyles()
        }
    }

    get(HomePage.slug) {
        call.respondHtml {
            site(HomePage)
        }
    }

    get(AboutPage.slug) {
        call.respondHtml {
            site(AboutPage)
        }
    }

    route(NotesPage.ROOT_SLUG) {
        get {
            val names = allNotes().toList()
            val index = NotesPage.Index(names)
            call.respondHtml {
                site(index)
            }
        }

        route("/{note-name}") {
            get {
                val noteName = call.pathParameters["note-name"]!!
                val noteNameFq = "${NotesPage.ROOT_SLUG}/$noteName"

                when (val markdownText = loadResourceText("${noteNameFq}.md")) {
                    null -> call.respondRedirect(NotesPage.ROOT_SLUG, permanent = false)
                    else -> {
                        call.respondHtml {
                            site(notesPage(noteNameFq, markdownText))
                        }
                    }
                }
            }
        }
    }
}

private suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
