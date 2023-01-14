package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import java.awt.Robot
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class KeyboardController(private val sounds: Sounds, private var resolver: Resolver, private var modeWeapons: Boolean) {

    private var deltaArr = LinkedList<Int>()
    private var nowSaving = false



    init {
        GlobalScreen.addNativeKeyListener(GlobalKeyListenerExample())
    }


    fun doSave(dig: Int) {
        if (!nowSaving) {
            safePrint("start save")
            nowSaving = true
            deltaArr.clear()
            deltaArr.push(dig)
            sounds.playStartSave()
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    nowSaving = false
                    sounds.playSaved()
                    resolver.deltaY = 0
                    safePrint("deltaArr = $deltaArr")
                    deltaArr.forEachIndexed { index, i ->
                        val tens = Math.pow(10.0, index.toDouble()).toInt()
                        resolver.deltaY += i * tens
                    }
                    safePrint("saved deltaY = ${resolver.deltaY}")
                }
            }, 2000)
        } else {
            deltaArr.push(dig)
        }
    }

    inner class GlobalKeyListenerExample : NativeKeyListener {
        override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
            safePrint("nativeKeyPressed: keyChar = ${nativeEvent?.keyChar} keyCode = ${nativeEvent?.keyCode} nativeEvent.keyLocation=${nativeEvent?.keyLocation}")
            when {
                modeWeapons && nativeEvent?.keyCode == 8 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedGun = resolver.selectedGun.prev()
                    sounds.mapGun(resolver.selectedGun)
                }

                modeWeapons && nativeEvent?.keyCode == 9 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedGun = resolver.selectedGun.next()
                    sounds.mapGun(resolver.selectedGun)
                }

                modeWeapons && nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedNasadka = resolver.selectedNasadka.prev()
                    sounds.mapNasadki(resolver.selectedNasadka)
                }

                modeWeapons && nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedNasadka = resolver.selectedNasadka.next()
                    sounds.mapNasadki(resolver.selectedNasadka)
                }


                modeWeapons && nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedRuchka = resolver.selectedRuchka.prev()
                    sounds.mapRuchki(resolver.selectedRuchka)
                }

                modeWeapons && nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedRuchka = resolver.selectedRuchka.next()
                    sounds.mapRuchki(resolver.selectedRuchka)
                }


                modeWeapons && nativeEvent?.keyCode == 11 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedScope = resolver.selectedScope.prev()
                    sounds.mapScope(resolver.selectedScope)
                }

                modeWeapons && nativeEvent?.keyCode == 83 && nativeEvent.keyLocation == 4 -> {
                    resolver.selectedScope = resolver.selectedScope.next()
                    sounds.mapScope(resolver.selectedScope)
                }

                modeWeapons && nativeEvent?.keyCode == 28 && nativeEvent.keyLocation == 4 -> {
                    sounds.mapGun(resolver.selectedGun)
                    Thread.sleep(500)
                    sounds.mapNasadki(resolver.selectedNasadka)
                    Thread.sleep(600)
                    sounds.mapRuchki(resolver.selectedRuchka)
                    Thread.sleep(600)
                    sounds.mapScope(resolver.selectedScope)
                }


                // Enter delta Y from num pad
                !modeWeapons && nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 4 -> {
                    doSave(1)
                }
                !modeWeapons && nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 4 -> {
                    doSave(2)
                }
                !modeWeapons && nativeEvent?.keyCode == 4 && nativeEvent.keyLocation == 4 -> {
                    doSave(3)
                }
                !modeWeapons && nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 4 -> {
                    doSave(4)
                }
                !modeWeapons && nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 4 -> {
                    doSave(5)
                }
                !modeWeapons && nativeEvent?.keyCode == 7 && nativeEvent.keyLocation == 4 -> {
                    doSave(6)
                }
                !modeWeapons && nativeEvent?.keyCode == 8 && nativeEvent.keyLocation == 4 -> {
                    doSave(7)
                }
                !modeWeapons && nativeEvent?.keyCode == 9 && nativeEvent.keyLocation == 4 -> {
                    doSave(8)
                }
                !modeWeapons && nativeEvent?.keyCode == 10 && nativeEvent.keyLocation == 4 -> {
                    doSave(9)
                }
                !modeWeapons && nativeEvent?.keyCode == 11 && nativeEvent.keyLocation == 4 -> {
                    doSave(0)
                }






                // On/off script by selecting weapon, 1 - sniper, 2 assault rifle
                nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 1 -> {
                    //resolver.dmrSelected = true
                }
                nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 1 -> {
                    resolver.buttonOnOff = false
                    //sounds.playOff()
                }

                nativeEvent?.keyCode == 4 && nativeEvent.keyLocation == 1 -> {
                    resolver.buttonOnOff = false
                   // sounds.playOff()
                }

                nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 1 -> {
                    resolver.buttonOnOff = false
                    //sounds.playOff()
                }

                nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 1 -> {
                    resolver.buttonOnOff = false
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
                    resolver.setQOn()
                }

                // E
                nativeEvent?.keyCode == 18 && nativeEvent.keyLocation == 1 -> {
                    resolver.setEOn()
                }
            }
        }

        override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {
            //safePrint("nativeKeyReleased: keyChar = ${nativeEvent?.keyChar} keyCode = ${nativeEvent?.keyCode} nativeEvent.keyLocation=${nativeEvent?.keyLocation}")
            when {
                // Q
                nativeEvent?.keyCode == 16 && nativeEvent.keyLocation == 1 -> {
                    resolver.setQOff()
                }

                // E
                nativeEvent?.keyCode == 18 && nativeEvent.keyLocation == 1 -> {
                    resolver.setEOff()
                }
            }
        }
    }
}