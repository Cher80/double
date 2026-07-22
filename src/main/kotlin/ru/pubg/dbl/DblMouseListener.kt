package ru.pubg.dbl

import com.sun.jna.platform.win32.WinDef.WPARAM
import com.sun.jna.platform.win32.WinUser.MSLLHOOKSTRUCT
import ru.pubg.MouseJNI
import ru.pubg.MouseJNI.Companion.WM_MOUSEMOVE
import ru.pubg.MouseJNI.Companion.WM_MOUSEWHEEL
import ru.pubg.safePrint
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class DblMouseListener(val mouseJNI: MouseJNI, val dblResolver: DblResolver, val dblSounds: DblSounds, val screener: DblScreener, val useImageDetect: Boolean) : MouseJNI.Callback {

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

        if (wParam != WM_MOUSEMOVE && wParam != WM_MOUSEWHEEL) {
            if (wParam == MouseJNI.WM_RBUTTONUP) {

                executorFixedThreadPool.submit {
                    safePrint("${Thread.currentThread().name} WM_RBUTTONUP")
                    if (dblResolver.enable) dblSounds.playOn()
                }
            }

            /**
             * Turn macros on off by mouse side buttons
             */
//            if (wParam == MouseJNI.WM_MOUSEUP && mouseData == MouseJNI.WM_MOUSE_SIDE_UP) {
//                executorFixedThreadPool.submit {
//                    safePrint("${Thread.currentThread().name} WM_MOUSE_SIDE_UP")
//                    sounds.playOn()
//                    resolver.buttonOnOff = true
//                }
//            }
//
//            if (wParam == MouseJNI.WM_MOUSEUP && mouseData == MouseJNI.WM_MOUSE_SIDE_DOWN) {
//
//                executorFixedThreadPool.submit {
//                    safePrint("${Thread.currentThread().name} WM_MOUSE_SIDE_DOWN")
//                    sounds.playOff()
//                    resolver.buttonOnOff = false
//                }
//            }

            if (!softwareClick && dblResolver.enable && wParam == MouseJNI.WM_LBUTTONDOWN) {

                executorFixedThreadPool.submit {
                    safePrint("${Thread.currentThread().name} WM_LBUTTONUP deltaY=${dblResolver.getRealDeltaY()}")
                    if (useImageDetect) {
                        screener.makeScreen(1)
                        makeShotScreenDetect(
                            yDelta = dblResolver.getRealDeltaY(),
                            max = 1,
                            cur = 1
                        )
                    } else {
                        makeShot(
                            yDelta = dblResolver.getRealDeltaY(),
                            max = 1,
                            cur = 1
                        )
                    }
                }
            }
        }
    }




    fun makeShot(yDelta: Int, max: Int, cur: Int) {
        softwareClick = true
        val sin = sin(qeRadians)
        val cos = cos(qeRadians)
        val xDeltaQe = (sin * yDelta *
                when {
                    dblResolver.q -> 1
                    dblResolver.e -> -1
                    else -> 0
                }).toInt()
        val yDeltaQe = if (dblResolver.q || dblResolver.e) (cos * yDelta).toInt() else yDelta
        Thread.sleep(65) // если уменьшать то при быстром тапе не будет двойного выстрела
        mouseJNI.mouseLeftUp()
        Thread.sleep(30) // усли уменьшать сильный разброс после 2 выстрела
        mouseJNI.mouseMove(xDeltaQe.toLong(), yDeltaQe.toLong())
        Thread.sleep(10)
        mouseJNI.mouseLeftDown()
        Thread.sleep(20)
        mouseJNI.mouseLeftUp()
        Thread.sleep(5)


        softwareClick = false
        if (cur < max) {
            makeShot(
                yDelta = yDelta,
                max = max,
                cur = cur + 1
            )
        }
    }


    fun makeShotScreenDetect(yDelta: Int, max: Int, cur: Int) {
        softwareClick = true
        Thread.sleep(530) // если уменьшать то при быстром тапе не будет двойного выстрела
        mouseJNI.mouseLeftUp()
        screener.makeScreen(2)
        val pos = screener.compareBoth()
        val dx = pos.first
        val dy = pos.second / 1.59f
        println("my dx=$dx dy=$dy ")
        Thread.sleep(15)
        mouseJNI.mouseMove((dx).toLong(), (dy).toLong())
        Thread.sleep(20)
        mouseJNI.mouseLeftDown()
        Thread.sleep(15)
        mouseJNI.mouseLeftUp()
        Thread.sleep(5)

        softwareClick = false
        if (cur < max) {
            screener.makeScreen(1)
            makeShotScreenDetect(
                yDelta = yDelta,
                max = max,
                cur = cur + 1
            )
        }
    }
}