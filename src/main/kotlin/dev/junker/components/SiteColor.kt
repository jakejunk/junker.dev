package dev.junker.components

import kotlinx.css.Color

enum class SiteColor(value: String) {
    BackgroundDark("#06070A"),
    BackgroundMedium("#1D2434"),
    BackgroundLight("#2E3952"),
    ButtonColor("#161B27"),
    Primary("#1ABC9C"),
    PrimaryBright("#54E8CA"),
    Secondary("#ED6A5A"),
    SecondaryBright("#F29488"),
    PrimaryText("#F0E7D8"),
    SubtleText("#798BB4");

    val color = Color(value)
}
