package com.oddinstitute.temp

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import java.nio.file.Path

class PathData(var type: PathType) {
//    var x1: Float = 0f
//    var y1: Float = 0f
//    var x2: Float = 0f
//    var y2: Float = 0f
//    var x: Float = 0f
//    var y: Float = 0f

    var knot = PointF()
    var cp1 = PointF ()
    var cp2 = PointF ()


    override fun toString(): String {
//        return "Type: $type\n x1: $x1 y1: $y1\nx2: $x2 y2: $y2\nx: $x y: $y"
        return ""
    }

// MoveTo(val x: Float, val y: Float)
// LineTo(val x: Float, val y: Float)
// CurveTo(val x1: Float, val y1: Float, val x2: Float, val y2: Float, val x: Float, val y: Float)
// SmoothCurveTo(val x2: Float, val y2: Float, val x: Float, val y: Float)
// QuadraticTo(val x1: Float, val y1: Float, val x: Float, val y: Float)
// SmoothQuadraticTo(val x: Float, val y: Float)
}

