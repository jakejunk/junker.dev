package dev.junker.components

import dev.junker.components.drawer.renderDrawer
import dev.junker.components.footer.renderFooter
import dev.junker.components.main.renderMainContent
import dev.junker.components.page.Page
import kotlinx.html.*

fun HTML.renderWebPage(page: Page) {
    lang = "en-US"
    renderHead(page)
    body {
        renderDrawer(page)
        renderMainContent(page)
        renderFooter()
    }
}

private fun HTML.renderHead(page: Page) {
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