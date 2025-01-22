package dev.junker.components.pages

import dev.junker.components.asciiBanner.asciiBanner
import kotlinx.html.*

data object HomePage : Page.Content {
    override val title = "Jake Junker"
    override val name = "junker.dev"
    override val slug = "/"
    override val description = "Just a simple dev trying to make his way in the universe."
    override val content: FlowContent.() -> Unit = {
        asciiBanner()
    }
}
