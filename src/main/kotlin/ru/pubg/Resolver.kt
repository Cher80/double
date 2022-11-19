package ru.pubg

class Resolver(private val sounds: Sounds) {

    var deltaY = 120

    var enable = false
        private set

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

    private fun resolveEnabled() {
        val initialEnable = enable
        enable = dmrSelected && enterOptic
        when {
            enable && !initialEnable ->  {
                safePrint("Now on")
                sounds.playOn()
            }
            !enable && initialEnable ->  {
                safePrint("Now off")
                //sounds.playOff()
            }
        }
    }
}