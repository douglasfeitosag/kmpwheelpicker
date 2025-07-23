package dev.douglasfeitosa.kmpwheelpicker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform