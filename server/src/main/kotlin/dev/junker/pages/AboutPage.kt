package dev.junker.pages

import dev.junker.components.general.externalLink
import dev.junker.components.general.hiddenLink
import dev.junker.components.code.inlineCode
import dev.junker.components.general.rightFloatImage
import kotlinx.html.*

data object AboutPage : Page.Content {
    override val slug = "/about"
    override val title = "About"
    override val description = "Founded in 1993, Jake somehow got to the point of writing nonsense page descriptions for the internet."
    override val content: FlowContent.() -> Unit = {
        section {
            h2 { +"About me" }
            rightFloatImage("Headshot of Jake", "/assets/images/500x500_headshot.jpg", "Jake")
            p {
                +"I think computers are pretty neat."
            }
            p {
                +"From the moment I wrote my first line of "
                externalLink("XNA", xnaWikipediaLink)
                +" in high school computer science, I knew that I wanted to develop video games for a living. "
                +"That dream made me who I am today—a software engineer building "
                externalLink("CRUD", crudWikipediaLink)
                +" applications for large corporations"
                hiddenLink(".", missionAccomplishedLink)
                + " "
                +"Outside of work, I enjoy churning through the fun parts of new projects in the name of learning. "
                a(href = slug) { +"Websites" }
                +", game engines, and compilers are just some of the technologies I love tinkering with."
            }
            p {
                +"On top of my digital projects, "
                +"I try to stay balanced with a good amount of sports, hiking, and traveling. "
                +"I also enjoy trying to learn actual, human languages. "
                +"Someday, I'll even get good at one. "
                i {
                    lang = "de"
                    +"Vielleicht eines Tages."
                }
            }
        }

        section {
            h2 { +"About this site" }
            p {
                +"Throw some "
                externalLink("Ktor", ktorLink)
                +" into a pot, sprinkle in a healthy bit of "
                externalLink("Kotlin/Wasm", kotlinWasmLink)
                +", and you've got a website stewing. "
                +"The \"modern nostalgia\" aesthetic, inspired by me being a huge nerd, is composed of anything I can easily design myself. "
                +"Hope you like pixel art!"
            }
            p {
                +"Of course, tooling and style are just half the story. "
                +"What brings this all together, i.e., what keeps this fun, is a design philosophy centered on learning and experimentation. "
                +"Behind all the scribbling and half-completed projects lie a few guiding principles:"
                ul {
                    li {
                        +"Within reason, everything will be made from scratch"
                        sup { +"1" }
                        +"."
                    }
                    li {
                        +"Strive for a progressive enhancement strategy by default. "

                        ul {
                            li {
                                +"Said simply, use scripting responsibly."
                            }
                        }
                    }
                    li {
                        externalLink("AA WCAG", wcagLink)
                        +" compliance is the accessibility goal."
                    }
                }
            }
            p { +"Thanks for stopping by! \uD83D\uDE0A" }
        }

        section(classes = "footnotes") {
            p {
                sup { +"1" }
                +"Just like an apple pie."
            }
        }
    }
}

private const val xnaWikipediaLink = "https://en.wikipedia.org/wiki/Microsoft_XNA"
private const val crudWikipediaLink = "https://en.wikipedia.org/wiki/Create,_read,_update_and_delete"
private const val missionAccomplishedLink = "https://upload.wikimedia.org/wikipedia/commons/b/b5/Mission_Accomplished_banner_on_the_USS_Abraham_Lincoln_%28CVN-72%29_%281%29.jpg"
private const val ktorLink = "https://ktor.io/"
private const val kotlinWasmLink = "https://kotlinlang.org/docs/wasm-overview.html"
private const val wcagLink = "https://www.w3.org/WAI/WCAG22/quickref/"
