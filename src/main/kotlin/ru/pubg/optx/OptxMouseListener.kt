package ru.pubg.optx

import com.sun.jna.platform.win32.WinDef.WPARAM
import com.sun.jna.platform.win32.WinUser.MSLLHOOKSTRUCT
import ru.pubg.KeyboardJNI
import ru.pubg.MouseJNI
import ru.pubg.MouseJNI.Companion.WM_MOUSEMOVE
import ru.pubg.MouseJNI.Companion.WM_MOUSEWHEEL
import ru.pubg.dbl.DblResolver
import ru.pubg.dbl.DblScreener
import ru.pubg.dbl.DblSounds
import ru.pubg.safePrint
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.concurrent.thread
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class OptxMouseListener(val keyboardJNI: KeyboardJNI) : MouseJNI.Callback {

    val qeRadians =  (PI / 180f) * 20f
    var softwareClick = false
    var executorFixedThreadPool = Executors.newFixedThreadPool(5) as ThreadPoolExecutor


    override fun onMouseEvent(nCode: Int, wParam: WPARAM?, lParam: MSLLHOOKSTRUCT?) {
        //safePrint("nativeMousePressed: b=${e.button}  softwareClick=$softwareClick x=${e.x} y=${e.y} ")
        val wParam = wParam?.toInt() ?: -1
        val x = lParam?.pt?.x ?: -1
        val y = lParam?.pt?.y ?: -1
        val mouseData = lParam?.mouseData ?: -1
        val flags = lParam?.flags ?: -1
        val time = lParam?.time ?: -1

        if (wParam == MouseJNI.WM_RBUTTONUP || wParam == MouseJNI.WM_RBUTTONDOWN) {
            println("WM_LBUTTONUP")
            keyboardJNI.keyboardPress(
                shift = 0x10, // shift
                key =  0x55 // u
            )
        }
    }




}