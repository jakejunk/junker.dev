package dev.junker

import dev.junker.components.drawer.renderDrawer
import dev.junker.components.footer.renderFooter
import dev.junker.components.main.renderMainContent
import io.ktor.http.*
import kotlinx.html.*

sealed interface Page {
    val title: String
    val block: FlowContent.() -> Unit

    sealed interface Error : Page {
        val status get() = when (this) {
            InternalServerError -> HttpStatusCode.InternalServerError
            NotFound -> HttpStatusCode.NotFound
        }
    }

    sealed interface Content : Page {
        val slug: String
        val name: String
        val description: String
    }

    object InternalServerError : Error {
        override val title = "500 - Internal Server Error"
        override val block: FlowContent.() -> Unit = {
            +"view: Something went wrong."
        }
    }

    object NotFound : Error {
        override val title = "404 - Not Found"
        override val block: FlowContent.() -> Unit = {
            +"view: Page not found."
        }
    }

    object Home : Content {
        override val title = "Jake Junker"
        override val name = "junker.dev"
        override val slug = "/"
        override val description = "Just a simple dev trying to make his way in the universe."
        override val block: FlowContent.() -> Unit = {
            +"Under construction :)"
        }
    }

    object About : Content {
        override val title = "About - ${Home.title}"
        override val name = "/about"
        override val slug = "/about"
        override val description = "Founded in 1993, Jake somehow got to the point of writing nonsense page descriptions for the internet."
        override val block: FlowContent.() -> Unit = {
            h1(classes = "heading") { +"about" }

            section {
                h2(classes = "heading") { +"about-me" }
                p {
                    +"I'm Jake, and I'm a professional software engineer."
                }
                p {
                    +"From the moment I wrote my first line of "
                    a(href = "https://en.wikipedia.org/wiki/Microsoft_XNA") { +"XNA"  }
                    +", I knew that I wanted to develop video games for a living. "
                    +"That experience is what got me to where I am today—professionally writing Spring Boot applications in Java 8. "
                    +"Outside of work, I enjoy learning new things by starting projects and overcomplicating them. "
                    +"Websites, game engines, and programming languages are just some of the things I enjoy hand-rolled."
                }
                p {
                    +"But it's not all about computers! "
                    +"All the tech is balanced with a healthy serving of sports, hiking, and traveling. "
                    +"I also enjoy trying to learn actual, human languages. "
                    +"Someday, I'll even get good at one. "
                    i {
                        lang = "de"
                        +"Vielleicht eines Tages."
                    }
                }
            }

            hr()

            section {
                h2(classes = "heading") { +"about-this-site" }
                p {
                    +"This site is all about fun, learning, and embracing "
                    a(href = "https://en.wikipedia.org/wiki/Not_invented_here") { +"not-invented-here syndrome" }
                    +". The stack is fairly straightforward: a "
                    a(href = "https://www.digitalocean.com/products/droplets") { +"Droplet" }
                    +" for hosting, and "
                    a(href = "https://ktor.io/") { +"Ktor" }
                    +" for the server. Everything else will be made from scratch"
                    sup { +"1" }
                    +". "
                    +"In the spirit of learning, this site will attempt to follow a few guidelines:"
                    ul {
                        li {
                            a(href = "https://en.wikipedia.org/wiki/Progressive_enhancement") { +"Progressive enhancement" }
                            +" is the name of the game. This site should not require scripting in order to function."
                        }
                        li {
                            +"This site should aim for "
                            a(href = "https://www.w3.org/WAI/WCAG22/quickref/") { +"AA WCAG compliance" }
                            +"."
                        }
                        li { +"No matter what happens, make sure to have fun. \uD83D\uDE0A" }
                    }
                }
                p { +"Thanks for stopping by!" }
            }

            section(classes = "footnotes") {
                p {
                    sup { +"1" }
                    +"But first, I have to invent the universe."
                }
            }
        }
    }
}

fun HTML.renderPage(page: Page) {
    head {
        meta(charset = "utf-8")
        title(page.title)
        if (page is Page.Content) {
            meta(name = "description", content = page.description)
        }
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        renderFaviconStuff()
        renderFontStuff()
        styleLink("/styles.css")
    }
    body {
        renderDrawer(page)
        renderMainContent(page)
        renderFooter()
    }
}

private fun HEAD.renderFaviconStuff() {
    meta(name = "msapplication-TileColor", content = "#1abc9c")
    meta(name = "theme-color", content = "#1abc9c")
    link(rel="apple-touch-icon", href="/apple-touch-icon.png") { sizes = "180x180" }
    link(rel="icon", type="image/png", href="/favicon-32x32.png") { sizes = "32x32" }
    link(rel="icon", type="image/png", href="/favicon-16x16.png") { sizes = "16x16" }
    link(rel="manifest", href="/site.webmanifest")
    link(rel="mask-icon", href="/safari-pinned-tab.svg") { attributes["color"] = "#1abc9c" }
}

private fun HEAD.renderFontStuff() {
    link(rel = "preconnect", href = "https://fonts.googleapis.com")
    link(rel = "preconnect", href = "https://fonts.gstatic.com") { attributes["crossorigin"] = "" }
    styleLink("https://fonts.googleapis.com/css2?family=Source+Code+Pro:wght@200;300;700&display=swap")
    styleLink("https://fonts.googleapis.com/css2?family=Work+Sans:wght@300&display=swap")
}
