package com.oddinstitute.temp

import android.graphics.Color
import kotlinx.serialization.Serializable



//@Serializable
class Artwork ()
{
    var polygons: ArrayList<Polygon> = arrayListOf()

    constructor(svgFile: SvgFile) : this()
    {
        for (path in svgFile.paths)
        {
            // the pathdata might make multiple polygons.
            var pathString =
                    path.pathData.trimIndent()
                            .trimStart()
                            .trimEnd()
                            .trim()
                            .replace("\n", "")
                            .replace("z", "Z")

            // HERE, WE BREAK THE string by Zs
            val separatePolyPiecesStr =
                    pathString.split("(?<=Z)".toRegex())
                            .toTypedArray()

            val polygonsStringArr = arrayListOf<String>()
            for (any in separatePolyPiecesStr)
                if (any.isNotEmpty())
                    polygonsStringArr.add(any)

            // by now, we have all the separate pieces in the SAME path
            // now, let's convert each into a polygon
            for (pathPieceStr in polygonsStringArr)
            {
                // this string should become an arraylist of PathPieces
                val polygon = convertSinglePathDataToPolygon(pathPieceStr)
                polygon.fillColor = Color.parseColor(path.fillColor)
                polygon.strokeColor = Color.parseColor(path.strokeColor)
                polygon.strokeWidth = path.strokeWidth.toFloat()

                polygons.add(polygon)
            }
        }
    }
}
