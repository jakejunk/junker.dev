package dev.junker.components.maze

import dev.junker.*
import dev.junker.components.SiteColor
import dev.junker.components.general.*
import kotlinx.css.*
import kotlinx.css.properties.*

private const val gridWidth = 650
private const val controlWidth = 256
private const val gridControlsGapWidth = 32

fun CssBuilder.mazeStyles() {
    mazeGridStyles()
    mazeSidePanelStyles()
}

private fun CssBuilder.mazeGridStyles() {
    keyframes("fadeIn") {
        0 {
            opacity = 0
        }
        100 {
            opacity = 1
        }
    }

    mazePlaceholder.selector {
        secondaryTextGlow()
        animation += Animation(
            name = "fadeIn",
            duration = 0.75.s,
            timing = Timing.easeInOut,
            fillMode = FillMode.forwards
        )
        height = gridWidth.px
        margin = Margin(vertical = 2.rem, horizontal = LinearDimension.auto)
        maxWidth = gridWidth.px
        opacity = 0
        textAlign = TextAlign.center

        transform {
            opacity = 1
        }
    }

    maze.selector {
        wrappingRow(
            rowGap = 16.px,
            columnGap = gridControlsGapWidth.px
        )
        containerType = ContainerType.inlineSize
        justifyContent = JustifyContent.center
        margin = Margin(2.rem, (-1).rem + 5.px)
    }

    mazeGrid.selector {
        monospaceFont()
        aspectRatio = AspectRatio(1, 1)
        backgroundColor = SiteColor.BackgroundDarkish.color
        border = lightBorder(1.px)
        flexBasis = 100.pct.basis
        flexGrow = 1
        maxWidth = gridWidth.px

        display = Display.grid
        gridTemplateColumns = GridTemplateColumns.repeat("var(--grid-cols), 1fr")
    }

    mazeCell.selector {
        containerType = ContainerType.size
        position = Position.relative

        before {
            position = Position.absolute
            borderRadius = 1.rem
        }

        after {
            display = Display.block
            height = 100.pct
            position = Position.absolute
            width = 100.pct
        }

        "&${mazeNorthWall.selector}" {
            after {
                borderTop = lightBorder(1.px)
            }
        }

        "&${mazeSouthWall.selector}" {
            after {
                borderBottom = lightBorder(1.px)
            }
        }

        "&${mazeEastWall.selector}" {
            after {
                borderRight = lightBorder(1.px)
            }
        }

        "&${mazeWestWall.selector}" {
            after {
                borderLeft = lightBorder(1.px)
            }
        }

        "&${mazeVisited.selector}" {
            before {
                mazePoint(4, SiteColor.Tertiary.color)
            }
        }

        "&${mazeStart.selector}" {
            before {
                mazePoint(8, SiteColor.Primary.color)
            }
        }

        "&${mazeEnd.selector}" {
            before {
                mazePoint(8, SiteColor.Secondary.color)
            }
        }

        "&${mazeSideQuest.selector}" {
            before {
                mazePoint(8, SiteColor.TertiaryBright.color)
            }
        }
    }
}

private fun CssBuilder.mazePoint(size: Int, color: Color) {
    val halfIsh = size / 2 + 0.1

    backgroundColor = color

    width = size.px
    height = size.px
    left = 50.pct - halfIsh.px
    top = 50.pct - halfIsh.px
}

private fun CssBuilder.mazeSidePanelStyles() {
    mazeSidePane.selector {
        flexColumn(columnGap = 16.px)
        justifyContent = JustifyContent.spaceBetween
    }

    mazeControls.selector {
        flexColumn(columnGap = 16.px)
        height = LinearDimension.fitContent
        width = controlWidth.px
    }

    mazeActions.selector {
        flexRow(rowGap = 1.ch)
        flexDirection = FlexDirection.rowReverse

        label {
            flexColumn()
            flexBasis = FlexBasis("0")
            flexGrow = 1
            textAlign = TextAlign.center

            mazeAction.selector {
                property("-webkit-tap-highlight-color", "transparent")
                appearance = Appearance.none
                backgroundColor = Color.transparent
                border = Border.none
                borderRadius = cornerRadius
                color = Color.unset
                margin = Margin(0.px)
                padding = Padding(8.px)
                textAlign = TextAlign.center

                before {
                    pixelatedBackgroundImage("assets/images/action-icons.png", size = 128.px)
                    display = Display.inlineBlock
                    height = 32.px
                    width = 32.px
                }

                hover {
                    cursor = Cursor.pointer
                }

                active {
                    backgroundColor = SiteColor.BackgroundLight.color
                }

                "&${mazeActionNext.selector}" {
                    before {
                        backgroundPosition = RelativePosition("-32px -32px")
                    }
                }

                "&${mazeActionRewind.selector}" {
                    before {
                        backgroundPosition = RelativePosition("0px -64px")
                    }
                }

                "&.twitch" {
                    before {
                        animation += Animation(
                            name = "twitch",
                            duration = 1.s,
                            timing = Timing.easeInOut
                        )
                    }
                }
            }
        }
    }

    // When the splce area becomes too narrow, move controls underneath grid
    container("(max-width: ${gridWidth + controlWidth + gridControlsGapWidth - 1}.9px)") {
        mazeControls.selector {
            maxWidth = gridWidth.px
            width = 100.pct
        }
    }
}

// ====================================================================================================================

fun CssBuilder.mazeTabletStyles() {
    maze.selector {
        marginLeft = 0.px
        marginRight = 0.px
    }

    mazeControls.selector {
        flexColumn(columnGap = 16.px)
        width = LinearDimension.fitContent
    }

    mazeActions.selector {
        flexColumn(columnGap = 1.ch)
    }
}
