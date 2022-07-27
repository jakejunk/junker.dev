package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY

fun CSSBuilder.renderDrawerStyles() {
    rule(drawer.css) {
        position = Position.fixed
        height = 100.vh
        width = 100.vw
        pointerEvents = PointerEvents.none
    }

    rule(drawerBackground.css) {
        position = Position.absolute
        backgroundColor = SiteColor.BackgroundDark.color
        height = 100.vh
        width = 100.vw
        opacity = 0
        visibility = Visibility.hidden
        transition("opacity", 300.ms)
        transition("visibility", 300.ms)
    }

    rule(drawerContents.css) {
        flexColumn()
        position = Position.fixed
        bottom = 0.px
        width = 100.pct
        transform.translateY(100.pct)
        transition("transform", 300.ms)
        property("willChange", "transform")
        pointerEvents = PointerEvents.auto
    }

    rule(drawerButton.css) {
        glowingPixelatedBackgroundImage("/assets/images/8x8_menu_black.png")
        position = Position.absolute
        top = LinearDimension("-6ch")
        left = LinearDimension("2ch")
        height = LinearDimension("4ch")
        width = LinearDimension("4ch")
        pointerEvents = PointerEvents.auto
        property("touch-action", "manipulation")
    }

    rule("${drawer.css}:focus-within ${drawerBackground.css}") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule("${drawer.css}:focus-within ${drawerButton.css}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawer.css}:focus-within ${drawerContents.css}") {
        transform = Transforms.none
    }
}

fun CSSBuilder.renderDrawerTabletStyles() {
    tabletOrLarger {
        rule(drawer.css) {
            position = Position.static
            height = LinearDimension.auto
            width = LinearDimension.auto
            pointerEvents = PointerEvents.unset
        }

        rule(drawerBackground.css) {
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(drawerContents.css) {
            position = Position.static
            height = 100.pct
            width = LinearDimension.auto
            transform = Transforms.none
        }

        rule(drawerButton.css) {
            display = Display.none
        }
    }
}
