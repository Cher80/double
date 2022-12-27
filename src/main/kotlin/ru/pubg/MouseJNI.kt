package ru.pubg

import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinDef.*
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.platform.win32.WinUser.*


class MouseJNI {
    companion object {
        private const val MOUSEEVENTF_MOVE = 0x0001L
        private const val MOUSEEVENTF_LEFTDOWN = 0x0002L
        private const val MOUSEEVENTF_LEFTUP = 0x0004L
        private const val MOUSEEVENTF_VIRTUALDESK = 0x4000L
        private const val MOUSEEVENTF_ABSOLUTE = 0x8000L
        private const val MOUSEEVENTF_RELATIVE = 0x8000L


        const val WM_MOUSEMOVE = 512
        const val WM_LBUTTONDOWN = 513
        const val WM_LBUTTONUP = 514
        const val WM_RBUTTONDOWN = 516
        const val WM_RBUTTONUP = 517
        const val WM_MBUTTONDOWN = 519
        const val WM_MBUTTONUP = 520
        const val WM_MOUSEWHEEL = 522

        const val WM_MOUSEDOWN = 523
        const val WM_MOUSEUP = 524
        const val WM_MOUSE_SIDE_UP = 131072
        const val WM_MOUSE_SIDE_DOWN = 65536
    }

    interface Callback {
        fun onMouseEvent(wParam: Int, x: Int, y: Int, mouseData: Int, flags: Int, time: Int)
    }

    private var thrd: Thread? = null
    private var threadFinish = true
    private var isHooked = false
    private var hhk: HHOOK? = null
    private var mouseHook: LowLevelMouseProc? = null

    var callback: Callback? = null

    init {
        if (!Platform.isWindows()) {
            throw UnsupportedOperationException("Not supported on this platform.");
        }


            mouseHook = hookTheMouse()
            //Native.setProtected(true)



    }


    fun unsetMouseHook() {
        threadFinish = true
        if (thrd?.isAlive == true) {
            thrd?.interrupt()
            thrd = null
        }
        isHooked = false
    }

    fun setMouseHook() {
        thrd = Thread {
            try {
                if (!isHooked) {
                    hhk = User32.INSTANCE.SetWindowsHookEx(
                        User32.WH_MOUSE_LL,
                        mouseHook,
                        Kernel32.INSTANCE.GetModuleHandle(null),
                        0
                    )
                    isHooked = true
                    val msg = MSG()
                    while (User32.INSTANCE.GetMessage(msg, null, 0, 0) != 0) {
                        User32.INSTANCE.TranslateMessage(msg)
                        User32.INSTANCE.DispatchMessage(msg)
                        //System.out.print(isHooked)
                        println(isHooked)
                        if (!isHooked) {
                            break
                        }
                    }
                } else {
                    println("The Hook is already installed.")
                }
            } catch (e: Exception) {
                System.err.println(e.message)
                System.err.println("Caught exception in MouseHook!")
            }
        }
        threadFinish = false
        thrd?.start()
    }

    fun mouseMove(x: Long, y: Long) {
        val input = WinUser.INPUT()

        input.input.setType("mi")
        input.input.mi.dx = WinDef.LONG(x)
        input.input.mi.dy = WinDef.LONG(y)
        input.input.mi.mouseData = WinDef.DWORD(0)
        input.input.mi.dwFlags = WinDef.DWORD(
            MOUSEEVENTF_MOVE
                    or MOUSEEVENTF_VIRTUALDESK
            //or MOUSEEVENTF_ABSOLUTE
        )
        input.input.mi.time = WinDef.DWORD(0)

        sendInput(input)
    }

    fun mouseLeftClick() {
        val input = WinUser.INPUT()
        input.input.setType("mi")
        input.input.mi.mouseData = WinDef.DWORD(0)
        input.input.mi.dwFlags = WinDef.DWORD(
            MOUSEEVENTF_LEFTDOWN
                    or MOUSEEVENTF_LEFTUP
        )
        input.input.mi.time = WinDef.DWORD(0)
        sendInput(input)
    }

    fun mouseLeftDown() {
        val input = WinUser.INPUT()
        input.input.setType("mi")
        input.input.mi.mouseData = WinDef.DWORD(0)
        input.input.mi.dwFlags = WinDef.DWORD(
            MOUSEEVENTF_LEFTDOWN
        )
        input.input.mi.time = WinDef.DWORD(0)
        sendInput(input)
    }

    fun mouseLeftUp() {
        val input = WinUser.INPUT()
        input.input.setType("mi")
        input.input.mi.mouseData = WinDef.DWORD(0)
        input.input.mi.dwFlags = WinDef.DWORD(
            MOUSEEVENTF_LEFTUP
        )
        input.input.mi.time = WinDef.DWORD(0)
        sendInput(input)
    }

    private fun sendInput(input: INPUT) {
        val inArray = arrayOf(input)
        val cbSize = input.size() // mouse input struct size
        val nInputs = WinDef.DWORD(1) // number of inputs
        val result = User32.INSTANCE.SendInput(nInputs, inArray, cbSize)
        //println("result: $result")
    }


    private fun hookTheMouse(): LowLevelMouseProc? {

        return object : WinUser.LowLevelMouseProc {
            override fun callback(nCode: Int, wParam: WPARAM?, lParam: MSLLHOOKSTRUCT?): LRESULT? {
                //println("${Thread.currentThread().name} callback wParam=$wParam lParam?.pt?.x=${lParam?.pt?.x} lParam?.pt?.y=${lParam?.pt?.y} mouseData = ${lParam?.mouseData}")
                if (nCode >= 0) {
                    if (wParam?.toInt() != WM_MOUSEMOVE) {
                        callback?.onMouseEvent(
                            wParam = wParam?.toInt() ?: -1,
                            x = lParam?.pt?.x ?: -1,
                            y = lParam?.pt?.y ?: -1,
                            mouseData = lParam?.mouseData ?: -1,
                            flags = lParam?.flags ?: -1,
                            time = lParam?.time ?: -1,
                        )
                    }
                    if (threadFinish) {
                        User32.INSTANCE.PostQuitMessage(0)
                    }
                }

                return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, LPARAM(0))
            }
        }
    }
}