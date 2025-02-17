package dev.junker.components.drawer

import dev.junker.components.*
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CssBuilder.headerStyles() {
    keyframes("logoTwitch") {
        8 {
            transform { rotateZ(30.deg) }
        }
        16 {
            transform { rotateZ((-15).deg) }
        }
        24 {
            transform { rotateZ(7.deg) }
        }
        32 {
            transform { rotateZ(0.deg) }
        }
        100 {
            transform { rotateZ(0.deg) }
        }
    }

    mainHeader.selector {
        flexColumn()
        alignItems = Align.center
        flexGrow = 1.0
    }

    siteLogo.selector {
        glowingPixelatedBackgroundImage("/assets/images/16x16_logo_black.png")
        height = 64.px
        margin = Margin(24.px)
        width = 64.px
    }

    "${siteLogo.selector}:hover" {
        animation += Animation(
            name = "logoTwitch",
            duration = 1.s,
            timing = Timing.easeInOut,
            delay = 0.s,
            iterationCount = 1.times,
            direction = AnimationDirection.normal,
            fillMode = FillMode.none,
            playState = PlayState.running
        )
    }

    "${siteLogo.selector}${error.selector}" {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    headerNav.selector {
        columnGap = 1.ch
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        padding = Padding(left = 24.px, right = 24.px, bottom = 24.px)
        rowGap = 1.ch
        width = 100.pct
    }

    navLink.selector {
        textWithShadow(SiteColor.PrimaryText.color, "none")
        backgroundColor = SiteColor.BackgroundMedium.color
        border = light2pxBorder
        borderRadius = cornerRadius
        padding = Padding(vertical = 1.rem, horizontal = 2.ch)
        textDecoration = TextDecoration.none
        whiteSpace = WhiteSpace.nowrap
    }

    "${navLink.selector}:hover" {
        backgroundColor = SiteColor.BackgroundLight.color
    }

    "${navLink.selector}.selected" {
        primaryTextGlow()
    }
}

fun CssBuilder.headerTabletStyles() {
    mainHeader.selector {
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.spaceBetween
        maxWidth = 1300.px
        padding = Padding(horizontal = 1.rem)
        width = 100.pct
    }

    headerNav.selector {
        alignSelf = Align.flexEnd
        padding = Padding(left = 24.px, right = 24.px, top = 24.px, bottom = 0.px)
        rowGap = 1.rem
        width = LinearDimension.auto
    }

    navLink.selector {
        borderBottomLeftRadius = 0.px
        borderBottomRightRadius = 0.px
        borderBottomStyle = BorderStyle.none
        marginBottom = (-1.8).px
    }
}
