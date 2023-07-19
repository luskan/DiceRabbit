package biz.progmar.diceroller

import android.opengl.GLES32
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Rectangle {
    private val vertexBuffer: FloatBuffer
    private val vertices = floatArrayOf(
        -0.5f, 0.5f, 0.0f, // Top left point
        -0.5f, -0.5f, 0.0f, // Bottom left point
        0.5f, -0.5f, 0.0f, // Bottom right point
        0.5f, 0.5f, 0.0f // Top right point
    )

    init {
        // Initialize the vertex byte buffer for shape coordinates
        val bb = ByteBuffer.allocateDirect(
            // (# of coordinate values * 4 bytes per float)
            vertices.size * 4
        )

        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
    }

    fun draw() {
        GLES32.glEnableVertexAttribArray(0)
        GLES32.glVertexAttribPointer(0, 3, GLES32.GL_FLOAT, false, 0, vertexBuffer)
        GLES32.glDrawArrays(GLES32.GL_TRIANGLE_FAN, 0, 4)
        GLES32.glDisableVertexAttribArray(0)
    }
}
