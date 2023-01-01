package ru.pubg

import ru.pubg.items.*

class Resolver(private val sounds: Sounds, private var modeWeapons: Boolean) {


    var q = false
        private set
    var e = false
        private set

    var deltaY = 55
    var deltaYWeapon = 55

    var enable = false
        private set

    var selectedGun = Guns.MK12
        set(value) {
            field = value
            resolveEnabled()
        }

    var selectedRuchka = Ruschki.NO
        set(value) {
            field = value
            resolveEnabled()
        }
    var selectedNasadka = Nasadki.NO
        set(value) {
            field = value
            resolveEnabled()
        }
    var selectedScope = Scopes.X15
        set(value) {
            field = value
            resolveEnabled()
        }

    var globalDisable = false

    var buttonOnOff = false
        set(value) {
            field = value
            resolveEnabled()
        }

    var dmrSelected = false
        set(value) {
            field = value
            resolveEnabled()
        }
    var enterOptic = false
        set(value) {


            field = value
            resolveEnabled()
        }

    fun setQOn() {
        q = true
        e = false
    }

    fun setQOff() {
        q = false
    }

    fun setEOn() {
        q = false
        e = true
    }

    fun setEOff() {
        e = false
    }

    private fun resolveEnabled() {
        val initialEnable = enable
        //enable = dmrSelected && enterOptic && !globalDisable
        enable = buttonOnOff && !globalDisable
        when {
            enable && !initialEnable ->  {
                safePrint("Now on")
                //sounds.playOn()
            }
            !enable && initialEnable ->  {
                safePrint("Now off")
                //sounds.playOff()
            }
        }

        val s = Presets.obvesi.get(
            Presets.Obves(
                guns = selectedGun,
                nasadki = selectedNasadka,
                ruschki = selectedRuchka,
                scopes = selectedScope
            )
        )
        println("sssssss = $s")
        deltaYWeapon = Presets.getDeltaWeapon(
            Presets.Obves(
                guns = selectedGun,
                nasadki = selectedNasadka,
                ruschki = selectedRuchka,
                scopes = selectedScope
            )
        )
    }

    fun getRealDeltaY(): Int {
        return if (modeWeapons) {
            deltaYWeapon
        } else {
            deltaY
        }
    }
}