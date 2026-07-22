package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.sun.jna.platform.win32.User32
import ru.pubg.dbl.DblKeyListener
import ru.pubg.dbl.DblResolver
import ru.pubg.dbl.DblSounds
import ru.pubg.dbl.DblMouseListener
import ru.pubg.dbl.DblScreener
import ru.pubg.optx.OptxKeyListener
import ru.pubg.optx.OptxMouseListener
import ru.pubg.srnr.Mode
import java.awt.Robot


class Dvoechka {
    companion object {
        var mode = Mode.OPTIX
        @JvmStatic fun main(args : Array<String>) {
            safePrint("Starting")
            safePrint("User32.INSTANCE.GetSystemMetrics(User32.SM_CXSCREEN) = ${User32.INSTANCE.GetSystemMetrics(User32.SM_CXSCREEN)}")
            safePrint("User32.INSTANCE.GetSystemMetrics(User32.SM_CYSCREEN) = ${User32.INSTANCE.GetSystemMetrics(User32.SM_CYSCREEN)}")

            try {
                // Native keyboard and mouse li2stener
                GlobalScreen.registerNativeHook()
            } catch (ex: NativeHookException) {
                safePrint("There was a problem registering the native hook: ${ex.message}")
                System.exit(1)
            }

            val robot = Robot()
            val mouseJNI = MouseJNI()
            val keyboardJNI = KeyboardJNI()

            when (mode) {
                Mode.DBL -> {
                    val dblSounds = DblSounds()
                    val modeWeapons = true

                    val screener = DblScreener(
                        robot = robot
                    )

                    val dblResolver = DblResolver(sounds = dblSounds, modeWeapons = modeWeapons)

                    val dblMouseListener = DblMouseListener(
                        mouseJNI = mouseJNI,
                        dblResolver = dblResolver,
                        dblSounds = dblSounds,
                        screener = screener,
                        useImageDetect = false
                    )

                    val dblKeyListener = DblKeyListener(
                        dblResolver = dblResolver,
                        dblSounds = dblSounds,
                        modeWeapons = modeWeapons
                    )

                    mouseJNI.addListener(dblMouseListener)
                    keyboardJNI.addListener(dblKeyListener)
                }
                Mode.OPTIX -> {
                    val optxMouseListener = OptxMouseListener(
                        keyboardJNI = keyboardJNI
                    )
                    val optxKeyListener = OptxKeyListener(
                        keyboardJNI = keyboardJNI
                    )
                    mouseJNI.addListener(optxMouseListener)
                    keyboardJNI.addListener(optxKeyListener)
                }
            }


            //mouseJNI.setMouseHook()
        }


    }
}