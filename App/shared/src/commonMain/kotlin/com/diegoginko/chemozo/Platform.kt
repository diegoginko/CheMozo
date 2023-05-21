package com.diegoginko.chemozo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform