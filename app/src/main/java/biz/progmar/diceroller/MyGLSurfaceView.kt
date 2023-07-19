package biz.progmar.diceroller

import android.content.Context
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private val renderer: Renderer

    init {
        // Create an OpenGL ES 3.2 context
        setEGLContextClientVersion(3)

        renderer = MyRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}


class MyRenderer : GLSurfaceView.Renderer {

    // Add your shaders
    private val vertexShaderCode =
        "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = vPosition;" +
                "}"

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"

    private var colorHandle: Int = 0

    // And add the color (RGBA)
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    private val circleColor = floatArrayOf(1f, 0.76953125f, 0.22265625f, 1.0f)


    private lateinit var mRectangle: Rectangle
    private lateinit var mCircle: Circle

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background frame color
        GLES32.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        val vertexShader: Int = loadShader(GLES32.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // create empty OpenGL ES Program
        val mProgram = GLES32.glCreateProgram().also {

            // add the vertex shader to program
            GLES32.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES32.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES32.glLinkProgram(it)
        }

        // get handle to fragment shader's vColor member
        colorHandle = GLES32.glGetUniformLocation(mProgram, "vColor")

        // Add the program to the environment
        GLES32.glUseProgram(mProgram)

        mRectangle = Rectangle()
        mCircle = Circle()
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        // create a vertex shader type (GLES32.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES32.GL_FRAGMENT_SHADER)
        return GLES32.glCreateShader(type).also { shader ->

            // add the source code to the shader and compile it
            GLES32.glShaderSource(shader, shaderCode)
            GLES32.glCompileShader(shader)
        }
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES32.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT or GLES32.GL_DEPTH_BUFFER_BIT)

        // Set color for drawing the rectangle
        GLES32.glUniform4fv(colorHandle, 1, color, 0)

        mRectangle.draw()

        // Change color for the circle or keep it same based on your requirement
        GLES32.glUniform4fv(colorHandle, 1, circleColor, 0)

        mCircle.draw()
    }
}
