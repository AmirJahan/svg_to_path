package com.oddinstitute.temp

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import kotlinx.serialization.Serializable


/**
 * Artwork ~ Svg
 * Polygon ~ Path
 * Single Piece Paths
 */

@Serializable
class Polygon
{
    lateinit var pieces: ArrayList<PathPiece>

    var fillColor = Color.BLACK
    var strokeColor = Color.TRANSPARENT
    var strokeWidth = 0.0f
    var closed = true

    var strokeLineCap : Paint.Cap = Paint.Cap.ROUND
    var fillType = Path.FillType.EVEN_ODD
}