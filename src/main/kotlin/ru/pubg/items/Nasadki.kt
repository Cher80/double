package ru.pubg.items

enum class Nasadki {
    PLAMYA, KOMPENS, NO;

    companion object {
        val set = setOf<Nasadki>(PLAMYA, KOMPENS, NO)
    }
}