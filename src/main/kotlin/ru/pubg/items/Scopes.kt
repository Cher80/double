package ru.pubg.items

enum class Scopes {
    X4, X6, X8, X15;

    companion object {
        val set = setOf<Scopes>(X4,  X6, X8, X15)
    }
}