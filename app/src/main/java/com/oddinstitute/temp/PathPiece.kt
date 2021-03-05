package com.oddinstitute.temp

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import kotlinx.serialization.Serializable
import java.nio.file.Path

@Serializable
class PathPiece(var type: PathType)
{
    var knot: PointF
        get() = PointF()
        set(value) = TODO()
    var cp1: PointF
        get() = PointF()
        set(value) = TODO()
    var cp2: PointF
        get() = PointF()
        set(value) = TODO()
}


