package biz.progmar.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class MainActivity : AppCompatActivity() {
    private lateinit var gLView: MyGLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gLView = MyGLSurfaceView(this)
        val layout = findViewById<RelativeLayout>(R.id.main_layout)
        layout.addView(gLView)
    }
}