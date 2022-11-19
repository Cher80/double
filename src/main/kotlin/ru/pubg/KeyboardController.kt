package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import java.awt.Robot
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class KeyboardController(private val sounds: Sounds, private var resolver: Resolver) {

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
                // Enter delta Y from num pad
                nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 4 -> {
                    doSave(1)
                }
                nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 4 -> {
                    doSave(2)
                }
                nativeEvent?.keyCode == 4 && nativeEvent.keyLocation == 4 -> {
                    doSave(3)
                }
                nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 4 -> {
                    doSave(4)
                }
                nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 4 -> {
                    doSave(5)
                }
                nativeEvent?.keyCode == 7 && nativeEvent.keyLocation == 4 -> {
                    doSave(6)
                }
                nativeEvent?.keyCode == 8 && nativeEvent.keyLocation == 4 -> {
                    doSave(7)
                }
                nativeEvent?.keyCode == 9 && nativeEvent.keyLocation == 4 -> {
                    doSave(8)
                }
                nativeEvent?.keyCode == 10 && nativeEvent.keyLocation == 4 -> {
                    doSave(9)
                }
                nativeEvent?.keyCode == 11 && nativeEvent.keyLocation == 4 -> {
                    doSave(0)
                }

                // On/off script by selecting weapon, 1 - sniper, 2 assault rifle
                nativeEvent?.keyCode == 2 && nativeEvent.keyLocation == 1 -> {
                    resolver.dmrSelected = true
                }
                nativeEvent?.keyCode == 3 && nativeEvent.keyLocation == 1 -> {
                    resolver.dmrSelected = false
                    resolver.enterOptic= false
                }

                nativeEvent?.keyCode == 4 && nativeEvent.keyLocation == 1 -> {
                    resolver.dmrSelected = false
                    resolver.enterOptic= false
                }

                nativeEvent?.keyCode == 5 && nativeEvent.keyLocation == 1 -> {
                    resolver.dmrSelected = false
                    resolver.enterOptic= false
                }

                nativeEvent?.keyCode == 6 && nativeEvent.keyLocation == 1 -> {
                    resolver.dmrSelected = false
                    resolver.enterOptic= false
                }


                // ESC disable scope
                nativeEvent?.keyCode == 1 && nativeEvent.keyLocation == 1 -> {
                    resolver.enterOptic= false
                }

                // Tab disable scope
                nativeEvent?.keyCode == 15 && nativeEvent.keyLocation == 1 -> {
                    resolver.enterOptic = false
                }

            }
        }
    }
}