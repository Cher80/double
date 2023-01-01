package ru.pubg

class Resolver2(private val sounds: Sounds) {


    var q = false
        private set
    var e = false
        private set

    var deltaY = 55

    var enable = false
        private set

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
    }
}