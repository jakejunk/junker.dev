package dev.junker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
