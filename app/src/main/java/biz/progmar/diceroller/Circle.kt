package biz.progmar.diceroller

import android.opengl.GLES32
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Circle {

    private var vertexBuffer: FloatBuffer
    private val COORDS_PER_VERTEX = 3
    private var vertexCount: Int
    private val circleCoords: FloatArray

    init {
        val radius = 0.5f
        val numPoints = 100
        val circlePoints = ArrayList<Float>()

        // Add the center point of circle
        circlePoints.add(0.0f)
        circlePoints.add(0.0f)
        circlePoints.add(0.0f)

        for (i in 0 until numPoints) {
            val percent = (i.toFloat() / (numPoints-1).toFloat())
            val rad = percent * 2f * PI

            // Add point on the unit circle
            val outerX = radius * cos(rad).toFloat()
            val outerY = radius * sin(rad).toFloat()

            circlePoints.add(outerX)
            circlePoints.add(outerY)
            circlePoints.add(0.0f)
        }

        circleCoords = circlePoints.toFloatArray()
        vertexCount = circleCoords.size / COORDS_PER_VERTEX

        val bb = ByteBuffer.allocateDirect(circleCoords.size * 4) // (# of coordinate values * 4 bytes per float)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(circleCoords)
        vertexBuffer.position(0)
    }

    fun draw() {
        GLES32.glEnableVertexAttribArray(0)
        GLES32.glVertexAttribPointer(0, COORDS_PER_VERTEX, GLES32.GL_FLOAT, false, COORDS_PER_VERTEX * 4, vertexBuffer)
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_FAN, 0, vertexCount)
        GLES32.glDisableVertexAttribArray(0)
    }
}
