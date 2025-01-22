package dev.junker.components.pages

import dev.junker.components.general.externalLink
import dev.junker.components.general.hiddenLink
import dev.junker.components.general.inlineCode
import kotlinx.html.*

data object AboutPage : Page.Content {
    override val title = "About - ${HomePage.title}"
    override val name = "/about"
    override val slug = "/about"
    override val description = "Founded in 1993, Jake somehow got to the point of writing nonsense page descriptions for the internet."
    override val content: FlowContent.() -> Unit = {
        h1 { +"about" }

        section {
            h2 { +"about-me" }
            img(classes = "img-right", alt = "Headshot of Jake", src = "/assets/images/500x500_headshot.jpg")
            p {
                +"I'm Jake, and I write code every so often."
            }
            p {
                +"From the moment I wrote my first line of "
                externalLink("XNA", xnaWikipediaLink)
                +" in high school computer science, I knew that I wanted to develop video games for a living. "
                +"That dream got me to where I am today—building Spring Boot applications for large companies"
                hiddenLink(".", missionAccomplishedLink)
                + " "
                +"Outside of work, I enjoy starting new projects and churning through the fun parts "
                +"in the name of learning new things. "
                a(href = slug) { +"Websites" }
                +", game engines, and compilers are just some of the things I enjoy tinkering with."
            }
            p {
                +"On top of my digital projects, "
                +"I try to stay balanced with a good amount of sports, hiking, and traveling. "
                +"I also enjoy trying to learn actual, human languages. "
                +"Someday, I'll even get good at one. "
            }
            p {
                i {
                    lang = "de"
                    +"Vielleicht eines Tages."
                }
            }
        }

        hr()

        section {
            h2 { +"about-this-site" }
            p {
                +"This site is a journey, not a destination. "
                +"There is no "
                inlineCode("1.0")
                +" release or monetization scheme in the works; trying new things is the only plan. "
                +"It's my notepad and canvas, powered by a fairly straightforward stack: a "
                externalLink("Droplet", dropletsLink)
                +" for hosting, and "
                externalLink("Ktor", ktorLink)
                +" for the server. Everything else will be made from scratch"
                sup { +"1" }
                +"."
            }
            p {
                +"This site will attempt to follow a few guiding principles:"
                ul {
                    li {
                        +"Scripting should not be required for anything essential (i.e. progressive enhancement)."
                    }
                    li {
                        externalLink("AA WCAG", wcagLink)
                        +" compliance is desired."
                    }
                    li {
                        +"Never stop experimenting or having fun. \uD83D\uDE0A"
                    }
                }
            }
            p { +"Thanks for stopping by!" }
        }

        section(classes = "footnotes") {
            p {
                sup { +"1" }
                +"But first, I must invent the universe."
            }
        }
    }

}

private const val xnaWikipediaLink = "https://en.wikipedia.org/wiki/Microsoft_XNA"
private const val missionAccomplishedLink = "https://upload.wikimedia.org/wikipedia/commons/b/b5/Mission_Accomplished_banner_on_the_USS_Abraham_Lincoln_%28CVN-72%29_%281%29.jpg"
private const val dropletsLink = "https://www.digitalocean.com/products/droplets"
private const val ktorLink = "https://ktor.io/"
private const val wcagLink = "https://www.w3.org/WAI/WCAG22/quickref/"
