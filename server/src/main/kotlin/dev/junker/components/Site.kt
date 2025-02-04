package dev.junker.components

import dev.junker.components.pages.Page
import dev.junker.components.pages.page
import dev.junker.stylesRoute
import kotlinx.html.*

fun HTML.site(page: Page) {
    head(page)
    page(page)
}

private fun HTML.head(page: Page) {
    lang = "en-US"
    head {
        meta(charset = "utf-8")
        title(page.title)
        if (page is Page.Content) {
            meta(name = "description", content = page.description)
        }
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        faviconStuff()
        fontStuff()
        styleLink(stylesRoute)
        script(type = "application/javascript", src = "/assets/scripts/dev.junker.browser.js") {
            async = true
            defer = true
        }
    }
}

private fun HEAD.faviconStuff() {
    meta(name = "msapplication-TileColor", content = "#1abc9c")
    meta(name = "theme-color", content = "#1abc9c")
    link(rel="apple-touch-icon", href="/apple-touch-icon.png") { sizes = "180x180" }
    link(rel="icon", type="image/png", href="/favicon-32x32.png") { sizes = "32x32" }
    link(rel="icon", type="image/png", href="/favicon-16x16.png") { sizes = "16x16" }
    link(rel="manifest", href="/site.webmanifest")
    link(rel="mask-icon", href="/safari-pinned-tab.svg") { attributes["color"] = "#1abc9c" }
}

private fun HEAD.fontStuff() {
    link(rel = "preconnect", href = "https://fonts.googleapis.com")
    link(rel = "preconnect", href = "https://fonts.gstatic.com") { attributes["crossorigin"] = "" }
    styleLink("https://fonts.googleapis.com/css2?family=Source+Code+Pro:wght@200;300;700&display=swap")
    styleLink("https://fonts.googleapis.com/css2?family=Work+Sans:wght@300&display=swap")
}
