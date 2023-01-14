package ru.pubg.items

import ru.pubg.safePrint

object Presets {

    data class Obves(
        val guns: Guns,
        val nasadki: Nasadki,
        val ruschki: Ruschki,
        val scopes: Scopes
    )

    val obvesi = mapOf<Obves, Int>(
        Obves(
            guns = Guns.MK12,
            nasadki = Nasadki.NO,
            ruschki = Ruschki.NO,
            scopes = Scopes.X15,
        ) to 300,
        Obves(
            guns = Guns.MK12,
            nasadki = Nasadki.PLAMYA,
            ruschki = Ruschki.NO,
            scopes = Scopes.X15,
        ) to 280,

        Obves(
            guns = Guns.MK12,
            nasadki = Nasadki.KOMPENS,
            ruschki = Ruschki.NO,
            scopes = Scopes.X15,
        ) to 280,

        Obves(
            guns = Guns.MK12,
            nasadki = Nasadki.NO,
            ruschki = Ruschki.LEGKAYA,
            scopes = Scopes.X15,
        ) to 210,

        Obves(
            guns = Guns.MK12,
            nasadki = Nasadki.PLAMYA,
            ruschki = Ruschki.LEGKAYA,
            scopes = Scopes.X15,
        ) to 185,

        Obves(
            guns = Guns.MK12,
            nasadki = Nasadki.KOMPENS,
            ruschki = Ruschki.LEGKAYA,
            scopes = Scopes.X15,
        ) to 185,






        Obves(
            guns = Guns.MINI,
            nasadki = Nasadki.NO,
            ruschki = Ruschki.NO,
            scopes = Scopes.X15,
        ) to 285,
        Obves(
            guns = Guns.MINI,
            nasadki = Nasadki.KOMPENS,
            ruschki = Ruschki.NO,
            scopes = Scopes.X15,
        ) to 270,
        Obves(
            guns = Guns.MINI,
            nasadki = Nasadki.PLAMYA,
            ruschki = Ruschki.NO,
            scopes = Scopes.X15,
        ) to 270
    )


    fun getDeltaWeapon(obves: Obves): Int {
        val obves15 =  obves.copy(
            scopes = Scopes.X15
        )

        val delta15 = obvesi.get(obves15) ?:0

        //x8 0.68
        //x6 0.51
        //x4 0.37

        val scopedDelta = when (obves.scopes) {
            Scopes.X4 -> delta15 * 0.37f
                Scopes.X6 -> delta15 * 0.51f
            Scopes.X8 -> delta15 * 0.68f
            Scopes.X15 -> delta15 * 1f
        }

        safePrint("delta15=$delta15 scopedDelta=$scopedDelta obves.scopes=${obves.scopes} obves.guns=${obves.guns} obves.nasadki=${obves.nasadki} obves.ruschki=${obves.ruschki}")
        val verticalDelted = scopedDelta / 1.8f
        return verticalDelted.toInt()


    }


}