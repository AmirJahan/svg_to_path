package com.oddinstitute.temp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout


class MainActivity : AppCompatActivity()
{
    lateinit var boom : FrameLayout


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boom = findViewById<FrameLayout>(R.id.canvasView)

        val polyData = convertToData("temp")
        val drawingView1 = DrawView(this, polyData)
        boom.addView(drawingView1)


//        var sim = SimpleDraw (this)
//        boom.addView(sim)


    }
}





