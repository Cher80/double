package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.NativeHookException

class Dvoechka {
    companion object {
        @JvmStatic fun main(args : Array<String>) {
            safePrint("Starting")

            try {
                // Native keyboard and mouse li2stener
                GlobalScreen.registerNativeHook()
            } catch (ex: NativeHookException) {
                safePrint("There was a problem registering the native hook: ${ex.message}")
                System.exit(1)
            }

            val sounds = Sounds()

            val resolver = Resolver(sounds = sounds)

            val keyboardController = KeyboardController(
                sounds = sounds,
                resolver = resolver
            )

            val mouseController = MouseController(
                resolver = resolver
            )

            val pinger = Pinger(
                resolver = resolver
            )
        }
    }
}