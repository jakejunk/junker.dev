package dev.junker.components

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY

fun CSSBuilder.renderTerminalDrawerStyles() {
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
}

fun CSSBuilder.renderTerminalDrawerTabletStyles() {
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
    }
}
