package dev.junker.components.page

import kotlinx.html.*

data object HomePage : Page.Content {
    override val title = "Jake Junker"
    override val name = "junker.dev"
    override val slug = "/"
    override val description = "Just a simple dev trying to make his way in the universe."
    override val block: FlowContent.() -> Unit = {
        renderAsciiBanner()
        hr()
    }

    private fun FlowContent.renderAsciiBanner() {
        div(classes = "ascii-banner-container") {
            role = "heading"
            attributes["aria-level"] = "1"
            attributes["aria-label"] = "junker.dev"
            span(classes = "ascii-banner junker") {
                +"""
                        o8o                         oooo                           
                        `"'                         `888                           
                       oooo oooo  oooo  ooo. .oo.    888  oooo   .ooooo.  oooo d8b 
                       `888 `888  `888  `888P"Y88b   888 .8P'   d88' `88b `888""8P 
                        888  888   888   888   888   888888.    888ooo888  888     
                        888  888   888   888   888   888 `88b.  888    .o  888     
                        888  `V88V"V8P' o888o o888o o888o o888o `Y8bod8P' d888b    
                        888                                                        
                    .o. 88P                                                        
                    `Y888P                                                         
                """.trimIndent()
            }
            span(classes = "ascii-banner-group") {
                span(classes = "ascii-banner dot") {
                    +"""
                            
                            
                            
                            
                            
                        .o. 
                        Y8P 
                    """.trimIndent()
                }
                span(classes = "ascii-banner dev") {
                    +"""
                              .o8                       
                             "888                       
                         .oooo888   .ooooo.  oooo    ooo
                        d88' `888  d88' `88b  `88.  .8' 
                        888   888  888ooo888   `88..8'  
                        888   888  888    .o    `888'   
                        `Y8bod88P" `Y8bod8P'     `8'    
                    """.trimIndent()
                }
            }
        }
    }
}
