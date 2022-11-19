package ru.pubg

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.InputEvent
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


class MouseController(private val resolver: Resolver) {

    var robot = Robot()
    var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    var softwareClick = false


    init {
        val mouseListener = GlobalMouseListenerImpl()
        GlobalScreen.addNativeMouseListener(mouseListener)
        GlobalScreen.addNativeMouseMotionListener(mouseListener)
    }






    fun makeShot(yDelta: Int, max: Int, cur: Int) {
        softwareClick = true
        val x = MouseInfo.getPointerInfo().location.x
        val y = MouseInfo.getPointerInfo().location.y
        safePrint("makeShot: x=$x y=$y yDelta=$yDelta max=$max cur=$cur")
        robot.mouseMove(x, y + yDelta)
        val task = object : Runnable {
            override fun run() {
                safePrint("makeShot: RUN ${Thread.currentThread()}")
                robot.mouseRelease(InputEvent.BUTTON1_MASK)
                Thread.sleep(70)
                robot.mousePress(InputEvent.BUTTON1_MASK)
                robot.mouseRelease(InputEvent.BUTTON1_MASK)
                Thread.sleep(20)
                softwareClick = false
                if (cur < max) {
                    makeShot(
                        yDelta = yDelta,
                        max = max,
                        cur = cur + 1
                    )
                } else {
                    // Move down a little after the last shoot
                    val xAfterShot = MouseInfo.getPointerInfo().location.x
                    val yAfterShot = MouseInfo.getPointerInfo().location.y
                    robot.mouseMove(xAfterShot, yAfterShot + yDelta / 5)
                }
            }
        }
        executor.schedule(task, 50, TimeUnit.MILLISECONDS)
    }


    inner class GlobalMouseListenerImpl : NativeMouseInputListener {
        override fun nativeMousePressed(e: NativeMouseEvent) {
            safePrint("nativeMousePressed: b=${e.button}  softwareClick=$softwareClick x=${e.x} y=${e.y} ")

            if (e.button == NativeMouseEvent.BUTTON2) {
                resolver.enterOptic = !resolver.enterOptic
            }

            if (!softwareClick && resolver.enable && e.button == NativeMouseEvent.BUTTON1) {
                safePrint("Mouse Released: DO")
                makeShot(
                    yDelta = resolver.deltaY,
                    max = 1,
                    cur = 1
                )
            }
        }
    }
}