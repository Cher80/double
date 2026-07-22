package ru.pubg.optx

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import ru.pubg.KeyboardJNI
import ru.pubg.dbl.DblResolver
import ru.pubg.dbl.DblSounds
import ru.pubg.next
import ru.pubg.prev
import ru.pubg.safePrint



class OptxKeyListener(val keyboardJNI: KeyboardJNI) : NativeKeyListener {
    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent?) {
        safePrint("nativeKeyPressed: keyChar = ${nativeEvent?.keyChar} keyCode = ${nativeEvent?.keyCode} nativeEvent.keyLocation=${nativeEvent?.keyLocation}")

    }

    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent?) {

    }
}