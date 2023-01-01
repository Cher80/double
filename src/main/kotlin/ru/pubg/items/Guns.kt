package ru.pubg.items

enum class Guns {
    MK12, MINI;

    companion object {
        val set = setOf<Guns>(Guns.MK12, Guns.MINI)
    }

}