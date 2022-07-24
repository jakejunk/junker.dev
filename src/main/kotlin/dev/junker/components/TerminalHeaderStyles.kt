package dev.junker.components

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.*

fun CSSBuilder.renderTerminalHeaderStyles() {
    rule(".terminal-drawer") {
        position = Position.fixed
        height = 100.vh
        width = 100.vw
        pointerEvents = PointerEvents.none
    }

    rule(".terminal-drawer-bg") {
        position = Position.absolute
        backgroundColor = SiteColor.BackgroundDark.color
        height = 100.vh
        width = 100.vw
        opacity = 0
        visibility = Visibility.hidden
        transition("opacity", 300.ms)
        transition("visibility", 300.ms)
    }

    rule(".terminal-drawer-contents") {
        flexColumn()
        position = Position.fixed
        bottom = 0.px
        width = 100.pct
        transform.translateY(100.pct)
        transition("transform", 300.ms)
        property("willChange", "transform")
        pointerEvents = PointerEvents.auto
    }

    rule(".terminal-drawer-button") {
        glowingPixelatedBackgroundImage("/assets/images/8x8_menu_black.png")
        position = Position.absolute
        top = LinearDimension("-6ch") - 4.px
        left = LinearDimension("2ch") + 4.px
        height = LinearDimension("4ch")
        width = LinearDimension("4ch")
        pointerEvents = PointerEvents.auto
        property("touch-action", "manipulation")
    }

    rule(".terminal-header") {
        beveledTerminalSurface()
        flexColumn()
        flexGrow = 1.0
        alignItems = Align.center
    }

    rule(".terminal-drawer:focus-within > .terminal-drawer-bg") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule(".terminal-drawer:focus-within .terminal-drawer-button") {
        pointerEvents = PointerEvents.none
    }

    rule(".terminal-drawer:focus-within .terminal-drawer-contents") {
        transform = Transforms.none
    }

    rule(".site-logo-image") {
        glowingPixelatedBackgroundImage("/assets/images/16x16_logo_black.png")
        height = LinearDimension("8ch")
        width = LinearDimension("8ch")
        margin(2.rem, 2.rem, 2.rem, 2.rem)
    }

    rule(".site-logo-image.error") {
        filter = "invert(60%) sepia(30%) saturate(645%) hue-rotate(318deg) brightness(106%) contrast(90%) drop-shadow(0px 0px 1ch ${SiteColor.Secondary.color.value})"
    }

    rule(".terminal-nav") {
        alignSelf = Align.flexEnd
        columnGap = ColumnGap("1ch")
        display = Display.flex
        flexWrap = FlexWrap.wrap
        justifyContent = JustifyContent.center
        marginBottom = 1.rem
        paddingLeft = 1.rem
        paddingRight = 1.rem
        rowGap = RowGap("1ch")
        width = 100.pct
    }

    rule(".nav-link") {
        backgroundColor = SiteColor.BackgroundMedium.color
        borderRadius = LinearDimension("1ch")
        color = SiteColor.PrimaryText.color
        padding(vertical = 1.rem, horizontal = LinearDimension("3ch"))
        textDecoration = TextDecoration.none
        whiteSpace = WhiteSpace.nowrap
    }

    rule(".nav-link:hover") {
        backgroundColor = SiteColor.BackgroundDark.color
    }

    rule(".nav-link.selected") {
        primaryTextGlow()
    }
}

private fun CSSBuilder.glowingPixelatedBackgroundImage(url: String) {
    backgroundImage = Image("url($url)")
    backgroundSize = "contain"
    backgroundRepeat = BackgroundRepeat.noRepeat
    filter = "invert(74%) sepia(47%) saturate(462%) hue-rotate(115deg) brightness(98%) contrast(95%) drop-shadow(0px 0px 1ch ${SiteColor.Primary.color.value})"
    property("image-rendering", "pixelated")
}

fun CSSBuilder.renderTerminalHeaderTabletStyles() {
    tabletOrLarger {
        rule(".terminal-drawer") {
            position = Position.static
            height = LinearDimension.auto
            width = LinearDimension.auto
            pointerEvents = PointerEvents.unset
        }

        rule(".terminal-drawer-bg") {
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(".terminal-drawer-contents") {
            position = Position.static
            height = 100.pct
            width = LinearDimension.auto
            transform = Transforms.none
        }

        rule(".terminal-drawer-button") {
            display = Display.none
        }

        rule(".terminal-header") {
            borderTopLeftRadius = LinearDimension("1ch")
            borderBottomLeftRadius = LinearDimension("1ch")
        }

        rule(".terminal-nav") {
            alignItems = Align.stretch
            flexDirection = FlexDirection.column
            margin(vertical = 2.rem)
            paddingRight = 0.px
            rowGap = RowGap("1rem")
        }

        rule(".nav-link") {
            borderTopRightRadius = 0.px
            borderBottomRightRadius = 0.px
            padding(horizontal = LinearDimension("2ch"))
        }
    }
}
