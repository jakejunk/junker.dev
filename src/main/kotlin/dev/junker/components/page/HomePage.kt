package dev.junker.components.page

import kotlinx.html.FlowContent

object HomePage : Page.Content {
    override val title = "Jake Junker"
    override val name = "junker.dev"
    override val slug = "/"
    override val description = "Just a simple dev trying to make his way in the universe."
    override val block: FlowContent.() -> Unit = {
        +"Under construction :)"
    }
}
