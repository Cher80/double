package ru.pubg

import java.awt.MouseInfo
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin


class MouseController(private val resolver: Resolver,
                      private val mouseJNI: MouseJNI,
                      private val sounds: Sounds,
                        private val screener: Screener,
                      private val useImageDetect: Boolean
) {

    val qeRadians =  (PI / 180f) * 20f
    var softwareClick = false
    var executorFixedThreadPool = Executors.newFixedThreadPool(5) as ThreadPoolExecutor

    init {
        val mouseCallbackImpl = MouseCallbackImpl()
        mouseJNI.callback = mouseCallbackImpl
        executorFixedThreadPool.submit {
            safePrint("warmUp")
        }
    }







    fun makeShot(yDelta: Int, max: Int, cur: Int) {
        softwareClick = true
        val sin = sin(qeRadians)
        val cos = cos(qeRadians)
        val xDeltaQe = (sin * yDelta *
                when {
                    resolver.q -> 1
                    resolver.e -> -1
                    else -> 0
                }).toInt()
        val yDeltaQe = if (resolver.q || resolver.e) (cos * yDelta).toInt() else yDelta
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


    inner class MouseCallbackImpl : MouseJNI.Callback {
        override fun onMouseEvent(wParam: Int, x: Int, y: Int, mouseData: Int, flags: Int, time: Int) {
            //safePrint("nativeMousePressed: b=${e.button}  softwareClick=$softwareClick x=${e.x} y=${e.y} ")

            if (wParam == MouseJNI.WM_RBUTTONUP) {

                executorFixedThreadPool.submit {
                    safePrint("${Thread.currentThread().name} WM_RBUTTONUP")
                    if (resolver.enable) sounds.playOn()
                }


            }

            if (wParam == MouseJNI.WM_MOUSEUP && mouseData == MouseJNI.WM_MOUSE_SIDE_UP) {
                executorFixedThreadPool.submit {
                    safePrint("${Thread.currentThread().name} WM_MOUSE_SIDE_UP")
                    sounds.playOn()
                    resolver.buttonOnOff = true
                }
            }

            if (wParam == MouseJNI.WM_MOUSEUP && mouseData == MouseJNI.WM_MOUSE_SIDE_DOWN) {

                executorFixedThreadPool.submit {
                    safePrint("${Thread.currentThread().name} WM_MOUSE_SIDE_DOWN")
                    sounds.playOff()
                    resolver.buttonOnOff = false
                }

            }

            if (!softwareClick && resolver.enable && wParam == MouseJNI.WM_LBUTTONDOWN) {

                executorFixedThreadPool.submit {
                    safePrint("${Thread.currentThread().name} WM_LBUTTONUP deltaY=${resolver.getRealDeltaY()}")
                    if (useImageDetect) {
                        screener.makeScreen(1)
                        makeShotScreenDetect(
                            yDelta = resolver.getRealDeltaY(),
                            max = 1,
                            cur = 1
                        )
                    } else {
                        makeShot(
                            yDelta = resolver.getRealDeltaY(),
                            max = 1,
                            cur = 1
                        )
                    }
                }
            }


        }
    }
}