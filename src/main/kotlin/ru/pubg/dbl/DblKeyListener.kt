package ru.pubg.dbl

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import ru.pubg.next
import ru.pubg.prev
import ru.pubg.safePrint



class DblKeyListener(val dblResolver: DblResolver, val dblSounds: DblSounds, private var modeWeapons: Boolean) : NativeKeyListener {
    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        safePrint("nativeKeyPressed: keyChar = ${nativeEvent?.keyChar} keyCode = ${nativeEvent?.keyCode} nativeEvent.keyLocation=${nativeEvent?.keyLocation}")
        when {
            modeWeapons && nativeEvent?.keyCode == 8 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedGun = dblResolver.selectedGun.prev()
                dblSounds.mapGun(dblResolver.selectedGun)
            }

            modeWeapons && nativeEvent?.keyCode == 9 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedGun = dblResolver.selectedGun.next()
                dblSounds.mapGun(dblResolver.selectedGun)
            }

            modeWeapons && nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedNasadka = dblResolver.selectedNasadka.prev()
                dblSounds.mapNasadki(dblResolver.selectedNasadka)
            }

            modeWeapons && nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedNasadka = dblResolver.selectedNasadka.next()
                dblSounds.mapNasadki(dblResolver.selectedNasadka)
            }


            modeWeapons && nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedRuchka = dblResolver.selectedRuchka.prev()
                dblSounds.mapRuchki(dblResolver.selectedRuchka)
            }

            modeWeapons && nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedRuchka = dblResolver.selectedRuchka.next()
                dblSounds.mapRuchki(dblResolver.selectedRuchka)
            }


            modeWeapons && nativeEvent?.keyCode == 11 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedScope = dblResolver.selectedScope.prev()
                dblSounds.mapScope(dblResolver.selectedScope)
            }

            modeWeapons && nativeEvent?.keyCode == 83 && nativeEvent.keyLocation == 4 -> {
                dblResolver.selectedScope = dblResolver.selectedScope.next()
                dblSounds.mapScope(dblResolver.selectedScope)
            }

            modeWeapons && nativeEvent?.keyCode == 28 && nativeEvent.keyLocation == 4 -> {
                dblSounds.mapGun(dblResolver.selectedGun)
                Thread.sleep(500)
                dblSounds.mapNasadki(dblResolver.selectedNasadka)
                Thread.sleep(600)
                dblSounds.mapRuchki(dblResolver.selectedRuchka)
                Thread.sleep(600)
                dblSounds.mapScope(dblResolver.selectedScope)
            }


            // Enter delta Y from num pad
            !modeWeapons && nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(1)
            }
            !modeWeapons && nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(2)
            }
            !modeWeapons && nativeEvent?.keyCode == 4 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(3)
            }
            !modeWeapons && nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(4)
            }
            !modeWeapons && nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(5)
            }
            !modeWeapons && nativeEvent?.keyCode == 7 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(6)
            }
            !modeWeapons && nativeEvent?.keyCode == 8 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(7)
            }
            !modeWeapons && nativeEvent?.keyCode == 9 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(8)
            }
            !modeWeapons && nativeEvent?.keyCode == 10 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(9)
            }
            !modeWeapons && nativeEvent?.keyCode == 11 && nativeEvent.keyLocation == 4 -> {
                dblResolver.doSave(0)
            }






            // On/off script by selecting weapon, 1 - sniper, 2 assault rifle
            nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 1 -> {
                //resolver.dmrSelected = true
            }
            nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 1 -> {
                dblResolver.buttonOnOff = false
                //sounds.playOff()
            }

            nativeEvent?.keyCode == 4 && nativeEvent.keyLocation == 1 -> {
                dblResolver.buttonOnOff = false
                // sounds.playOff()
            }

            nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 1 -> {
                dblResolver.buttonOnOff = false
                //sounds.playOff()
            }

            nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 1 -> {
                dblResolver.buttonOnOff = false
                //sounds.playOff()
            }


            /*
            // ESC disable scope
            nativeEvent?.keyCode == 1 && nativeEvent.keyLocation == 1 -> {
                resolver.enterOptic= false
            }

            // Tab disable scope
            nativeEvent?.keyCode == 15 && nativeEvent.keyLocation == 1 -> {
                resolver.enterOptic = false
            }

            // R reload
            nativeEvent?.keyCode == 19 && nativeEvent.keyLocation == 1 -> {
                resolver.enterOptic = false
            }

            // minus on timepad
            nativeEvent?.keyCode == 3658 && nativeEvent.keyLocation == 4 -> {
                resolver.globalDisable = !resolver.globalDisable
            }
            */


            // Q
            nativeEvent?.keyCode == 16 && nativeEvent.keyLocation == 1 -> {
                dblResolver.setQOn()
            }

            // E
            nativeEvent?.keyCode == 18 && nativeEvent.keyLocation == 1 -> {
                dblResolver.setEOn()
            }

            // ~
            nativeEvent?.keyCode == 41 && nativeEvent.keyLocation == 1 -> {
                if (dblResolver.buttonOnOff) {
                    dblSounds.playOff()
                    dblResolver.buttonOnOff = false
                } else {
                    dblSounds.playOn()
                    dblResolver.buttonOnOff = true
                }
            }
        }
    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {
        //safePrint("nativeKeyReleased: keyChar = ${nativeEvent?.keyChar} keyCode = ${nativeEvent?.keyCode} nativeEvent.keyLocation=${nativeEvent?.keyLocation}")
        when {
            // Q
            nativeEvent?.keyCode == 16 && nativeEvent.keyLocation == 1 -> {
                dblResolver.setQOff()
            }

            // E
            nativeEvent?.keyCode == 18 && nativeEvent.keyLocation == 1 -> {
                dblResolver.setEOff()
            }
        }
    }
}