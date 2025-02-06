package dev.junker.components.drawer

import dev.junker.components.*
import dev.junker.components.general.*
import dev.junker.components.main.mainContainer
import kotlinx.css.*
import kotlinx.css.properties.*

val drawerButtonHeight = 32.px

fun CssBuilder.drawerStyles() {
    rule(drawerContainer.selector) {
        pointerEvents = PointerEvents.none
        position = Position.fixed
        // TODO: Make more ergonomic
        declarations["height"] = "100vh; height: 100dvh"
        width = 100.vw
    }

    rule(drawerBackground.selector) {
        backgroundColor = SiteColor.BackgroundDark.color
        position = Position.absolute
        height = 100.pct
        width = 100.pct
        opacity = 0
        visibility = Visibility.hidden
        transition += Transition("opacity", 300.ms)
        transition += Transition("visibility", 300.ms)
    }

    rule(drawer.selector) {
        flexColumn()
        property("willChange", "transform")
        backgroundColor = SiteColor.BackgroundMedium.color
        borderTop = light2pxBorder
        bottom = 0.px
        pointerEvents = PointerEvents.auto
        position = Position.fixed
        transform.translateY(100.pct)
        transition += Transition("transform", 300.ms)
        width = 100.pct
    }

    rule(drawerButton.selector) {
        glowingPixelatedBackgroundImage("/assets/images/8x8_menu_black.png")
        property("touch-action", "manipulation")
        pointerEvents = PointerEvents.auto
        backgroundColor = Color.transparent
        border = Border.none
        position = Position.absolute
        top = (-34).px - 1.5.rem
        left = 50.pct - 16.px
        height = drawerButtonHeight
        width = drawerButtonHeight
    }

    rule("${drawerContainer.selector}:focus-within ${drawerBackground.selector}") {
        opacity = 0.75
        visibility = Visibility.visible
    }

    rule("${drawerContainer.selector}:focus-within ${drawerButton.selector}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawerContainer.selector}:focus-within + ${mainContainer.selector}") {
        pointerEvents = PointerEvents.none
    }

    rule("${drawerContainer.selector}:focus-within ${drawer.selector}") {
        transform = Transforms.none
    }
}

fun CssBuilder.drawerTabletStyles() {
    rule(drawerContainer.selector) {
        pointerEvents = PointerEvents.unset
        position = Position.static
        height = LinearDimension.auto
        width = LinearDimension.auto
    }

    rule(drawerBackground.selector) {
        height = LinearDimension.auto
        width = LinearDimension.auto
    }

    rule(drawer.selector) {
        borderTop = Border.none
        borderBottom = light2pxBorder
        alignItems = Align.center
        position = Position.static
        height = 100.pct
        width = LinearDimension.auto
        transform = Transforms.none
    }

    rule(drawerButton.selector) {
        display = Display.none
    }
}
