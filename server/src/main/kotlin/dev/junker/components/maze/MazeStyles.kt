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
            duration = 1.s,
            timing = Timing.easeInOut,
            fillMode = FillMode.forwards
        )
        height = gridWidth.px
        margin = Margin(vertical = 2.rem, horizontal = LinearDimension.auto)
        maxWidth = gridWidth.px
        opacity = 0
        textAlign = TextAlign.center
    }

    maze.selector {
        wrappingRow(
            rowGap = 16.px,
            columnGap = gridControlsGapWidth.px
        )
        flickerIn()
        animationDelay = 0.2.s
        containerType = ContainerType.inlineSize
        justifyContent = JustifyContent.center
        margin = Margin(2.rem, (-1).rem + 5.px)
        opacity = 0

        focusWithin {
            mazeGridOverlay.selector {
                opacity = 0
            }

            mazeGridCells.selector {
                opacity = 1
            }
        }
    }

    mazeGridContainer.selector {
        flexColumn()
        flexBasis = 100.pct.basis
        maxWidth = gridWidth.px
    }

    mazeStats.selector {
        flexRow(gap = 8.px)
        border = light2pxBorder()
        borderBottomStyle = BorderStyle.none
        borderTopLeftRadius = cornerRadius
        borderTopRightRadius = cornerRadius
        justifyContent = JustifyContent.spaceBetween
        padding = Padding(horizontal = 12.px)

        mazeSteps.selector {
            before {
                content = "Steps: ".quoted
                color = SiteColor.SubtleText.color
            }
        }

        mazeCollectedTreasures.selector {
            flexRow(alignment = Align.center, gap = 8.px)
        }

        mazeCollectedTreasure.selector {
            mazePoint(8, SiteColor.TertiaryBright.color)
        }

        mazeCollectedTreasurePlaceholder.selector {
            mazePoint(8, SiteColor.BackgroundLight.color)
        }
    }

    mazeGrid.selector {
        flexColumn()
        frostedGlass(SiteColor.BackgroundDark.color)
        monospaceFont()
        aspectRatio = AspectRatio(1, 1)
        border = lightBorder(1.px)
        position = Position.relative

        mazeGridOverlay.selector {
            flexColumn()
            border = lightBorder(1.px)
            height = 100.pct
            justifyContent = JustifyContent.center
            pointerEvents = PointerEvents.none
            position = Position.absolute
            width = 100.pct
        }

        mazeGridCells.selector {
            display = Display.grid
            flexBasis = 100.pct.basis
            flexGrow = 1
            gridTemplateColumns = GridTemplateColumns.repeat("var(--grid-cols), 1fr")
            opacity = 0.1

            mazeCell.selector {
                containerType = ContainerType.size
                position = Position.relative

                before {
                    position = Position.absolute
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

                "&${mazeTreasure.selector}" {
                    before {
                        mazePoint(8, SiteColor.TertiaryBright.color)
                    }
                }
            }
        }
    }
}

private fun CssBuilder.mazePoint(size: Int, color: Color) {
    val halfIsh = size / 2 + 0.1

    backgroundColor = color
    borderRadius = 1.rem

    width = size.px
    height = size.px
    left = 50.pct - halfIsh.px
    top = 50.pct - halfIsh.px
}

private fun CssBuilder.mazeSidePanelStyles() {
    mazeSidePane.selector {
        flexRow(gap = 16.px)
        justifyContent = JustifyContent.spaceBetween
        maxWidth = gridWidth.px
        width = 100.pct
    }

    mazeControls.selector {
        flexRow(gap = 16.px)
        height = LinearDimension.fitContent
        width = 100.pct
    }

    mazeActions.selector {
        flexRow(gap = 1.ch)
        flexDirection = FlexDirection.rowReverse
        touchAction = TouchAction.manipulation
        width = 100.pct

        label {
            flexColumn()
            flexBasis = FlexBasis("0")
            flexGrow = 1
            textAlign = TextAlign.center
            whiteSpace = WhiteSpace.pre

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

                "&${mazeActionRestart.selector}" {
                    before {
                        backgroundPosition = RelativePosition("-64px 0px")
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

                disabled {
                    cursor = Cursor.notAllowed

                    before {
                        opacity = 0.25
                    }

                    sibling("span") {
                        opacity = 0.25
                    }
                }
            }
        }
    }

    // When the play area becomes too narrow, move controls underneath grid
    container("(min-width: ${gridWidth + controlWidth + gridControlsGapWidth - 1}.9px)") {
        mazeSidePane.selector {
            width = LinearDimension.initial
        }

        mazeControls.selector {
            flexColumn(gap = 16.px)
        }

        mazeActions.selector {
            flexColumn(gap = 1.ch)
        }
    }
}

// ====================================================================================================================

fun CssBuilder.mazeTabletStyles() {
    maze.selector {
        marginLeft = 0.px
        marginRight = 0.px
    }
}
