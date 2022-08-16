package dev.junker.components.drawer

import dev.junker.*
import kotlinx.css.*
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateY

fun CSSBuilder.renderDrawerStyles() {
    rule(drawerContainer.css) {
        position = Position.fixed
        height = 100.vh
        width = 100.vw
        pointerEvents = PointerEvents.none
    }

    rule(drawerBackground.css) {
        backgroundColor = SiteColor.BackgroundDark.color
        position = Position.absolute
        height = 100.vh
        width = 100.vw
        opacity = 0
        visibility = Visibility.hidden
        transition("opacity", 300.ms)
        transition("visibility", 300.ms)
    }

    rule(drawer.css) {
        backgroundColor = SiteColor.BackgroundMedium.color
        borderTop = "solid 2px ${SiteColor.BackgroundLight.color.value}"
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

    rule("${drawerContainer.css}:focus-within ${drawerBackground.css}") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule("${drawerContainer.css}:focus-within ${drawerButton.css}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawerContainer.css}:focus-within ${drawer.css}") {
        transform = Transforms.none
    }
}

fun CSSBuilder.renderDrawerTabletStyles() {
    tabletOrLarger {
        rule(drawerContainer.css) {
            position = Position.static
            height = LinearDimension.auto
            width = LinearDimension.auto
            pointerEvents = PointerEvents.unset
        }

        rule(drawerBackground.css) {
            height = LinearDimension.auto
            width = LinearDimension.auto
        }

        rule(drawer.css) {
            borderTop = "none"
            borderBottom = "solid 2px ${SiteColor.BackgroundLight.color.value}"
            alignItems = Align.center
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
