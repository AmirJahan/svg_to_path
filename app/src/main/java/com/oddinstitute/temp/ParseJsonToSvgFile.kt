package com.oddinstitute.temp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

@Serializable
data class MyClass (var name: String)

fun encodeMyClass ()
{
    val myClass = MyClass ("Amir")
    val str = Json.encodeToString(myClass)
    Log.d("MyTag", "Encoded is: $str")
}


fun encodeArtworkToJson (artwork: Artwork)
{
    val string = Json.encodeToString(artwork)

    Log.d("MyTag", "Encoded is $string")
}


fun parseJsonToSvgFile ()
{
    // Serializing objects
    val data = Project("kotlinx.serialization", "Kotlin")
    val string = Json.encodeToString(data)
    Log.d("MyTag", string)



    /*
    // Deserializing back into objects
    val obj = Json.decodeFromString<Project>(string)
    Log.d("MyTag", obj.toString())
    println(obj) // Project(name=kotlinx.serialization, language=Kotlin)

    val newStr = """
            {
               "width": "256dp",
               "height": "256dp",
               "viewportWidth": "256",
               "viewportHeight": "256",

            }


        """.trimIndent()

    val svgObj = Json.decodeFromString<SvgData>(newStr)
    Log.d("MyTag", svgObj.toString())
    println(svgObj)

     */
}


@Serializable
data class Project(val name: String, val language: String)

@Serializable
data class SvgData (val width: String, val height: String,
                    val viewportWidth: String, val viewportHeight: String) //,
//                    val path: Array<path>)


@Serializable
data class path (val strokeWidth: String,
                 val pathData: String,
                 val fillColor: String,
                 val strokeColor: String)

