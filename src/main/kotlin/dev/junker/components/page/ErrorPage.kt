package dev.junker.components.page

import kotlinx.html.FlowContent

object InternalServerErrorPage : Page.Error {
    override val title = "500 - Internal Server Error"
    override val block: FlowContent.() -> Unit = {
        +"view: Something went wrong."
    }
}

object NotFoundPage : Page.Error {
    override val title = "404 - Not Found"
    override val block: FlowContent.() -> Unit = {
        +"view: Page not found."
    }
}
