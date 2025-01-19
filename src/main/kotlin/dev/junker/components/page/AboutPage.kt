package dev.junker.components.page

import dev.junker.components.externalLink
import kotlinx.html.*

data object AboutPage : Page.Content {
    override val title = "About - ${HomePage.title}"
    override val name = "/about"
    override val slug = "/about"
    override val description = "Founded in 1993, Jake somehow got to the point of writing nonsense page descriptions for the internet."
    override val block: FlowContent.() -> Unit = {
        h1 { +"about" }

        section {
            h2 { +"about-me" }
            img(classes = "img-right", alt = "Headshot of Jake", src = "/assets/images/500x500_headshot.jpg")
            p {
                +"I'm Jake, and I'm a professional software engineer."
            }
            p {
                +"From the moment I wrote my first line of "
                externalLink("XNA", xnaWikipediaLink)
                +", I knew that I wanted to develop video games for a living. "
                +"This aspiration got me to where I am today—writing Spring Boot applications for large corporations"
                a(href = missionAccomplishedLink) { +"." }
                + " "
                +"Outside of work, I enjoy learning new things by starting projects and overcomplicating them. "
                +"Websites, game engines, and compilers are just some of the things I enjoy tinkering with."
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
                +"There is no 1.0 release or monetization scheme in the works; trying new things is the only plan. "
                +"It's my scratchpad and canvas, powered by a fairly straightforward stack: a "
                externalLink("Droplet", dropletsLink)
                +" for hosting, and "
                externalLink("Ktor", ktorLink)
                +" for the server. Everything else will be made from scratch"
                sup { +"1" }
                +"."
            }
            p {
                +"In the spirit of learning, this site will attempt to follow a few guidelines:"
                ul {
                    li {
                        +"Scripting should not be required for anything essential (i.e. progressive enhancement)."
                    }
                    li {
                        externalLink("AA WCAG", wcagLink)
                        +" compliance is desired."
                    }
                    li {
                        +"Keep trying new things, and have fun. \uD83D\uDE0A"
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
