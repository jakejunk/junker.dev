package dev.junker.components.asciiBanner

import dev.junker.components.SiteColor
import dev.junker.components.general.monospaceFont
import dev.junker.components.general.primaryTextGlow
import dev.junker.components.general.secondaryTextGlow
import kotlinx.css.*
import kotlinx.css.properties.lh

fun CssBuilder.asciiBannerStyles() {
    rule(asciiBannerContainer.selector) {
        alignSelf = Align.center
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.flexEnd
        margin = Margin(
            top = 1.rem,
            right = LinearDimension.auto,
            left = LinearDimension.auto
        )
        overflowX = Overflow.visible
        width = LinearDimension.minContent
    }

    rule(asciiBannerGroup.selector) {
        display = Display.flex
    }

    rule(asciiBanner.selector) {
        monospaceFont(
            fontWeight = FontWeight.normal,
            fontSize = 2.25.vw,
            lineHeight = 2.8.vw.lh
        )
        whiteSpace = WhiteSpace.pre
    }

    rule("${asciiBanner.selector}.junker") {
        marginBottom = (-2).em
    }

    rule("${asciiBanner.selector}.junker::before") {
        content = junker
        primaryTextGlow()
    }

    rule("${asciiBanner.selector}.dot::before") {
        content = dot
        secondaryTextGlow()
    }

    rule("${asciiBanner.selector}.dev::before") {
        content = dev
        color = SiteColor.PrimaryText.color
    }
}

fun CssBuilder.asciiBannerTabletStyles() {
    rule(asciiBannerContainer.selector) {
        justifyContent = JustifyContent.center
        width = LinearDimension.fitContent
    }

    rule(asciiBannerGroup.selector) {
        marginTop = 0.rem
    }

    rule(asciiBanner.selector) {
        fontSize = LinearDimension.inherit
        lineHeight = 1.25.rem.lh
    }
}

// ====================================================================================================================

private val junker =
    """
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
    """.trimIndent().quotedContent()

private val dot =
    """
            
            
            
            
            
        .o. 
        Y8P 
    """.trimIndent().quotedContent()

val dev =
    """
              .o8                       
             "888                       
         .oooo888   .ooooo.  oooo    ooo
        d88' `888  d88' `88b  `88.  .8' 
        888   888  888ooo888   `88..8'  
        888   888  888    .o    `888'   
        `Y8bod88P" `Y8bod8P'     `8'    
    """.trimIndent().quotedContent()

private fun String.quotedContent(): QuotedString {
    return replace("'", "\\'")
        .lines()
        .joinToString("\\A ")
        .quoted
}
