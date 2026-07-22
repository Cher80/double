package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.sun.jna.platform.win32.BaseTSD
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.DWORD
import com.sun.jna.platform.win32.WinDef.WORD
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.platform.win32.WinUser.INPUT

class KeyboardJNI() {

    fun addListener(nativeKeyListener: NativeKeyListener) {
        GlobalScreen.addNativeKeyListener(nativeKeyListener)
    }

    fun keyboardPress(shift: Long, key: Long) {
        println("${Thread.currentThread().name} keyboardPress 0")

        val inputA = WinUser.INPUT()
        inputA.type = DWORD(INPUT.INPUT_KEYBOARD.toLong())
        inputA.input.setType("ki") // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        inputA.input.ki.wScan = WORD(0)
        inputA.input.ki.time = DWORD(0)
        inputA.input.ki.dwExtraInfo = BaseTSD.ULONG_PTR(0)


        val inputShift = WinUser.INPUT()
        println("${Thread.currentThread().name} keyboardPress 0")
        inputShift.type = DWORD(INPUT.INPUT_KEYBOARD.toLong())
        inputShift.input.setType("ki") // Because setting INPUT_INPUT_KEYBOARD is not enough: https://groups.google.com/d/msg/jna-users/NDBGwC1VZbU/cjYCQ1CjBwAJ
        inputShift.input.ki.wScan = WORD(0)
        inputShift.input.ki.time = DWORD(0)
        inputShift.input.ki.dwExtraInfo = BaseTSD.ULONG_PTR(0)



        // Press "SHIFT"
        inputShift.input.ki.wVk = WORD(shift) // 0x41 a 0x55 u 0x10 shift
        inputShift.input.ki.dwFlags = DWORD(0) // keydown
        User32.INSTANCE.SendInput(DWORD(1), inputShift.toArray(1) as Array<INPUT?>?, inputShift.size())

        // Press "a"
        inputA.input.ki.wVk = WORD( key) //  0x41 a 0x55 u 0x10 shift
        inputA.input.ki.dwFlags = DWORD(0) // keydown
        User32.INSTANCE.SendInput(DWORD(1), inputA.toArray(1) as Array<INPUT?>?, inputA.size())



        // Release "a"
        inputA.input.ki.wVk = WORD( key) // 0x41 a 0x55 u 0x10 shift
        inputA.input.ki.dwFlags = DWORD(2) // keyup
        User32.INSTANCE.SendInput(DWORD(1), inputA.toArray(1) as Array<INPUT?>?, inputA.size())

        // Release "SHIFT"
        inputShift.input.ki.wVk = WORD(shift) //  0x41 a 0x55 u 0x10 shift
        inputShift.input.ki.dwFlags = DWORD(2) // keyup
        User32.INSTANCE.SendInput(DWORD(1), inputShift.toArray(1) as Array<INPUT?>?, inputShift.size())
        println("${Thread.currentThread().name} keyboardPress 3")

    }
}