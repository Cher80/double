package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException
import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.DWORD
import com.sun.jna.platform.win32.WinUser.INPUT


class Dvoechka {
    companion object {


        private var run = true
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

            val mouseJNI = MouseJNI()
            val sounds = Sounds()

            val resolver = Resolver(sounds = sounds)

            val keyboardController = KeyboardController(
                sounds = sounds,
                resolver = resolver
            )

            val mouseController = MouseController(
                resolver = resolver,
                mouseJNI = mouseJNI,
                sounds = sounds,
            )

            val pinger = Pinger(
                resolver = resolver
            )


            mouseJNI.mouseMove(-100L,100L)

            //Thread.sleep(500)
          // mouseJNI.mouseLeftClick()
            mouseJNI.setMouseHook()
            mouseJNI.mouseLeftDown()
            mouseJNI.mouseLeftUp()

//            val mouseHook =  MouseHook()
//            mouseHook.setMouseHook()
//            Thread.sleep(15 * 1000);
//            System.exit(0);
        }


    }
}