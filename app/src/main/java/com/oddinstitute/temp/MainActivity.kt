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

        boom = findViewById(R.id.canvasView)


        val svgPath_1 = SvgPath ("M95,130.4c2.4,32.1 51.7,28.5 52.3,0C144.9,98.2 95.6,101.8 95,130.4z",
                                 "#F9ED32",
                                 "1",
                                 "#EC008C")

        val svgPath_2 = SvgPath ("M59.9,54.2h73.6v36.2h-73.6z",
                                 "#000000",
                                 "1",
                                 "#00A79D")

        val svgFile_1 = SvgFile(arrayOf(svgPath_1, svgPath_2))


        val svgPath_3 = SvgPath ("M103.4,152.6c70.1,65.8 111.1,18.9 52.9,-45.3C83.8,34 38,85.5 103.4,152.6z",
                                 "#000000",
                                 "2",
                                 "#FFFFFF")

        val svgFile_2 = SvgFile(arrayOf(svgPath_3))


        val artwork_1 = Artwork(svgFile_1)
        val artwork_2 = Artwork(svgFile_2)


        encodeMyClass ()
//        encodeArtworkToJson(artwork_1)


        // old style
        /*
        val polyData = convertToData("M95,130.4c2.4,32.1,51.7,28.5,52.3,0C144.9,98.2,95.6,101.8,95,130.4z M200,200l200,0l0,200,l-200,0z")
        val drawingView1 = DrawView(this, polyData)
         */

        val drawView = DrawView (this, arrayOf(artwork_1, artwork_2))

        boom.addView(drawView)
    }
}





