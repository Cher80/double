package ru.pubg.dbl.items

enum class Nasadki {
    PLAMYA, KOMPENS, NO;

    companion object {
        val set = setOf<Nasadki>(PLAMYA, KOMPENS, NO)
    }
}