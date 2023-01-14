package ru.pubg

import org.opencv.core.*
import org.opencv.core.Point
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.awt.*
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO


class Screener(private val robot: Robot) {

    companion object {
        const val SCOPE_D_X = 0
        const val SCOPE_D_Y = 0

        const val TOP_DELTA = 0
        const val FIRST_SHOT_W = 50
        const val FIRST_SHOT_H = 50
        const val SECOND_SHOT_W = 100
        const val SECOND_SHOT_H = 100
        const val GENERATE_IMAGES = true
    }

    //val capture: VideoCapture
    var first: BufferedImage? = null
    var second: BufferedImage? = null
    val screenW = Toolkit.getDefaultToolkit().getScreenSize().width
    val screenH = Toolkit.getDefaultToolkit().getScreenSize().height

    init {
        println(System.getProperty("java.library.path"))
        System.loadLibrary("opencv_java460") // положить в C:\Windows\System32  x64/opencv_java460.dll
    }


    fun makeScreen(index: Int) {

        val t = System.currentTimeMillis()
        val screenRect = when (index) {
            1 -> {
                Rectangle(screenW / 2 + SCOPE_D_X, screenH / 2 + SCOPE_D_Y + TOP_DELTA, FIRST_SHOT_W, FIRST_SHOT_H)
            }
            2 -> {
                Rectangle(screenW / 2  + SCOPE_D_X - SECOND_SHOT_W / 2 + SCOPE_D_Y, screenH / 2 + TOP_DELTA, SECOND_SHOT_W, SECOND_SHOT_H)
            }
            else -> {
                Rectangle(0, 0, screenW, screenH)
            }
        }
        //val screenRect = Rectangle(Toolkit.getDefaultToolkit().getScreenSize())
        val captureF = Robot().createScreenCapture(screenRect)
        println("Robot captured in ${System.currentTimeMillis() - t}")
        val capture = toBufferedImageOfType(captureF, BufferedImage.TYPE_3BYTE_BGR)
        println("Robot converted in ${System.currentTimeMillis() - t}")
        when (index) {
            1 -> first = capture
            2 -> second = capture

        }
        if (GENERATE_IMAGES) {
            ImageIO.write(
                capture,
                "png",
                File("C:\\Users\\Эндрю\\IdeaProjects\\img\\screen${System.currentTimeMillis()}.png")
            )
            println("Robot writed in ${System.currentTimeMillis() - t}")
        }
    }

    fun compareBoth(): Pair<Int, Int> {
        val t = System.currentTimeMillis()
        val match_method = Imgproc.TM_CCORR_NORMED
        val img: Mat = bufferedImageToMat(second!!)
        val templ: Mat = bufferedImageToMat(first!!)


        // / Create the result matrix
        val result_cols: Int = img.cols() - templ.cols() + 1
        val result_rows: Int = img.rows() - templ.rows() + 1
        val result = Mat(result_rows, result_cols, CvType.CV_32FC1)

        println("compareBoth inted in ${System.currentTimeMillis() - t}")
        // / Do the Matching and Normalize
        Imgproc.matchTemplate(img, templ, result, match_method)
        println("matched in ${System.currentTimeMillis() - t}")
        Core.normalize(result, result, 0.0, 1.0, Core.NORM_MINMAX, -1, Mat())
        println("normalized in ${System.currentTimeMillis() - t}")

        // / Localizing the best match with minMaxLoc
        val mmr: Core.MinMaxLocResult = Core.minMaxLoc(result)

        val matchLoc: Point
        matchLoc = if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            mmr.minLoc
        } else {
            mmr.maxLoc
        }



        if (GENERATE_IMAGES) {

            // / Show me what you got
            Imgproc.rectangle(
                img, matchLoc, Point(
                    matchLoc.x + templ.cols(),
                    matchLoc.y + templ.rows()
                ), Scalar(0.0, 255.0, 0.0)
            )


            // Save the visualized detection.


            val withRectangele = matToBufferedImage(img)
            ImageIO.write(
                withRectangele,
                "png",
                File("C:\\Users\\Эндрю\\IdeaProjects\\img\\screen${System.currentTimeMillis()}.png")
            )
        }
        println("matchLoc.x = ${matchLoc.x} matchLoc.y = ${matchLoc.y}")
        val dX = (SECOND_SHOT_W/2 - matchLoc.x.toInt()) * -1
        val dY = matchLoc.y.toInt()
        println("dX = ${dX} dY = ${dY}")
        return dX to dY


    }


    fun bufferedImageToMat(bi: BufferedImage): Mat {
        val mat = Mat(bi.height, bi.width, CvType.CV_8UC3)
        val data: ByteArray = (bi.getRaster().getDataBuffer() as DataBufferByte).getData()
        mat.put(0, 0, data)
        return mat
    }


    fun toBufferedImageOfType(original: BufferedImage?, type: Int): BufferedImage? {
        requireNotNull(original) { "original == null" }

        // Don't convert if it already has correct type
        if (original.type == type) {
            return original
        }

        // Create a buffered image
        val image = BufferedImage(original.width, original.height, type)

        // Draw the image onto the new buffer
        val g = image.createGraphics()
        try {
            g.composite = AlphaComposite.Src
            g.drawImage(original, 0, 0, null)
        } finally {
            g.dispose()
        }
        return image
    }


    fun matToBufferedImage(matrix: Mat): BufferedImage {
        val mob = MatOfByte()
        Imgcodecs.imencode(".png", matrix, mob)
        return ImageIO.read(ByteArrayInputStream(mob.toArray()))
    }
}