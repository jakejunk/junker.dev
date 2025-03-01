package dev.junker.pages

import dev.junker.components.site
import dev.junker.components.siteStyles
import dev.junker.markdown.MarkdownMetadata
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CssBuilder

const val stylesRoute = "/styles.css"

fun Routing.routes(metaData: List<MarkdownMetadata>) {
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
            val index = NotesPage.Index(metaData)
            call.respondHtml {
                site(index)
            }
        }

        route("/{note-name}") {
            get {
                val noteName = call.pathParameters["note-name"]!!

                when (val notePage = notePage(noteName)) {
                    null -> call.respondRedirect(NotesPage.ROOT_SLUG, permanent = false)
                    else -> {
                        call.respondHtml {
                            site(notePage)
                        }
                    }
                }
            }
        }
    }

    route(ProjectsPage.ROOT_SLUG) {
        get {
            val index = ProjectsPage.Index()
            call.respondHtml {
                site(index)
            }
        }

        route("/{project-name}") {
            get {
                val projectName = call.pathParameters["project-name"]!!

                when (val projectPage = projectPage(projectName)) {
                    null -> call.respondRedirect(ProjectsPage.ROOT_SLUG, permanent = false)
                    else -> {
                        call.respondHtml {
                            site(projectPage)
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
