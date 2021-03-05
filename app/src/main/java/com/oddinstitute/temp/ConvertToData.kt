package com.oddinstitute.temp

import android.graphics.*

fun convertToData(singlePathDataString: String): ArrayList<Polygon>
{
    // These have to be read from XML
    val strokeWidthStr = "2"
    val fillColorStr = "#B4DFFB"
    val strokeColorStr = "#000000"
    val fillTypeStr = "evenOdd"
    val strokeLineCapStr = "round"
    var pathString =
            singlePathDataString.trimIndent()
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
    for (any in separatePolyPiecesStr) if (any.isNotEmpty()) polygonsStringArr.add(any)


    val delimiters: Array<Char> =
            arrayOf('M', 'm', 'L', 'l', 'C', 'c', 'S', 's', 'Q', 'q', 'T', 't', 'H', 'h', 'V', 'v')


    val pathPolygons = ArrayList<Polygon>()

    for (polygonPathDataStr in polygonsStringArr)
    {
        val thisPathPolygon = Polygon()
        var closed = false

        var workingString = polygonPathDataStr

        if (workingString.contains("Z"))
        {
            closed = true
            workingString = workingString.replace("Z", "")
        }

        val piecesStringArr = arrayListOf<String>(workingString)

        for (del in delimiters)
        {
            var temp = ArrayList<String>()
            temp.addAll(piecesStringArr.filterNotNull())
            piecesStringArr.clear()

            for (any in temp)
            {
                val thesePieces =
                        any.split("(?=$del)".toRegex())
                                .toTypedArray()

                for (each in thesePieces)
                {
                    if (each.isNotEmpty())
                    {
                        piecesStringArr.add("$each")
                    }
                }
            }
        }


        val piecesPathsArr = ArrayList<PathPiece>()
        var tempCurPoint = PointF()

        for (piece in piecesStringArr)
        {
            when (piece[0])
            {
                'M' ->
                {
                    val str = piece.replace("M", "")
                    val points = str.split(",")
                    val move = PathPiece(PathType.Move)
                    move.knot = PointF(points[0].toFloat(), points[1].toFloat())
                    tempCurPoint = move.knot
                    piecesPathsArr.add(move)
                }
                'L' ->
                {
                    val str = piece.replace("L", "")
                    val points = str.split(",")
                    val line = PathPiece(PathType.Line)
                    line.knot = PointF(points[0].toFloat(), points[1].toFloat())

                    tempCurPoint = line.knot
                    piecesPathsArr.add(line)
                }
                'l' ->
                {
                    val str = piece.replace("l", "")
                    val points = str.split(",")
                    val line = PathPiece(PathType.Line)
                    line.knot =
                            PointF(points[0].toFloat() + tempCurPoint.x,
                                   points[1].toFloat() + tempCurPoint.y)


                    tempCurPoint = line.knot
                    piecesPathsArr.add(line)
                }
                'H' ->
                {
                    val str = piece.replace("H", "")
                    val x = str.toFloat()
                    var y = 0f
                    if (piecesPathsArr.count() > 0) y = piecesPathsArr.last().knot.y
                    val point = PointF(x, y)
                    val hor = PathPiece(PathType.Horizontal)
                    hor.knot = PointF(x, y)

                    tempCurPoint = hor.knot
                    piecesPathsArr.add(hor)
                }
                'h' ->
                {
                    val str = piece.replace("h", "")
                    val x = str.toFloat()
                    var y = 0f
                    if (piecesPathsArr.count() > 0) y = piecesPathsArr.last().knot.y

                    val hor = PathPiece(PathType.Horizontal)
                    hor.knot = PointF(x + tempCurPoint.x, y)
                    tempCurPoint = hor.knot
                    piecesPathsArr.add(hor)
                }
                'V' ->
                {
                    val str = piece.replace("V", "")
                    val y = str.toFloat()
                    var x = 0f
                    if (piecesPathsArr.count() > 0) x = piecesPathsArr.last().knot.x

                    val ver = PathPiece(PathType.Horizontal)
                    ver.knot = PointF(x, y)

                    tempCurPoint = ver.knot
                    piecesPathsArr.add(ver)
                }
                'v' ->
                {
                    val str = piece.replace("v", "")
                    val y = str.toFloat()
                    var x = 0f
                    if (piecesPathsArr.count() > 0) x = piecesPathsArr.last().knot.x
                    val ver = PathPiece(PathType.Horizontal)
                    ver.knot = PointF(x, y + tempCurPoint.y)

                    tempCurPoint = ver.knot
                    piecesPathsArr.add(ver)
                }
                'C' ->
                {
                    val str =
                            piece.replace("C", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val curve = PathPiece(PathType.Curve)

                    curve.cp1 = PointF(points[0].toFloat(), points[1].toFloat())
                    curve.cp2 = PointF(points[2].toFloat(), points[3].toFloat())
                    curve.knot = PointF(points[4].toFloat(), points[5].toFloat())

                    tempCurPoint = curve.knot
                    piecesPathsArr.add(curve)
                }
                'c' ->
                {
                    val str =
                            piece.replace("c", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val curve = PathPiece(PathType.Curve)
                    curve.cp1 =
                            PointF(points[0].toFloat() + tempCurPoint.x,
                                   points[1].toFloat() + tempCurPoint.y)
                    curve.cp2 =
                            PointF(points[2].toFloat() + tempCurPoint.x,
                                   points[3].toFloat() + tempCurPoint.y)
                    curve.knot =
                            PointF(points[4].toFloat() + tempCurPoint.x,
                                   points[5].toFloat() + tempCurPoint.y)

                    tempCurPoint = curve.knot
                    piecesPathsArr.add(curve)
                }

                'S' ->
                {
                    val str =
                            piece.replace("S", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val smooth = PathPiece(PathType.SmoothCurve)
                    smooth.cp2 = PointF(points[0].toFloat(), points[1].toFloat())
                    smooth.knot = PointF(points[2].toFloat(), points[3].toFloat())


                    /**
                     * S
                     * Draws a cubic Bézier curve from the current point to (x,y).
                     * The first control point is assumed to be the reflection
                     * of the second control point on the previous command relative to the current point.
                     * If there is no previous command or if the previous command was not an C, c, S or s,
                     * assume the first control point is coincident with the current point.
                     */
                    if (piecesPathsArr.count() == 0)
                    {
                        smooth.cp1 = PointF()
                    }
                    else if (piecesPathsArr.last().type == PathType.Curve || piecesPathsArr.last().type == PathType.SmoothCurve)
                    {
                        val curX = piecesPathsArr.last().knot.x
                        val curY = piecesPathsArr.last().knot.y
                        val lastCommandSecondControlPointX = piecesPathsArr.last().cp2.x
                        val lastCommandSecondControlPointY = piecesPathsArr.last().cp2.y

                        val xReflectionOfSecondCP =
                                2 * tempCurPoint.x - lastCommandSecondControlPointX // + curX
                        val yReflectionOfSecondCP =
                                2 * tempCurPoint.y - lastCommandSecondControlPointY //+ curY
                        smooth.cp1 = PointF(xReflectionOfSecondCP, yReflectionOfSecondCP)
                    }
                    else
                    {
                        smooth.cp1 =
                                PointF(piecesPathsArr.last().knot.x, piecesPathsArr.last().knot.y)
                    }

                    tempCurPoint = smooth.knot
                    piecesPathsArr.add(smooth)
                }

                's' ->
                {
                    val str =
                            piece.replace("s", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val smooth = PathPiece(PathType.SmoothCurve)
                    smooth.cp2 =
                            PointF(points[0].toFloat() + tempCurPoint.x,
                                   points[1].toFloat() + tempCurPoint.y)
                    smooth.knot =
                            PointF(points[2].toFloat() + tempCurPoint.x,
                                   points[3].toFloat() + tempCurPoint.y)

                    /**
                     * S
                     * Draws a cubic Bézier curve from the current point to (x,y).
                     * The first control point is assumed to be the reflection
                     * of the second control point on the previous command relative to the current point.
                     * If there is no previous command or if the previous command was not an C, c, S or s,
                     * assume the first control point is coincident with the current point.
                     */
                    if (piecesPathsArr.count() == 0)
                    {
                        smooth.cp1 = PointF()
                    }
                    else if (piecesPathsArr.last().type == PathType.Curve || piecesPathsArr.last().type == PathType.SmoothCurve)
                    {
                        val lastCommandSecondControlPointX = piecesPathsArr.last().cp2.x
                        val lastCommandSecondControlPointY = piecesPathsArr.last().cp2.y

                        val xReflectionOfSecondCP =
                                2 * tempCurPoint.x - lastCommandSecondControlPointX // + curX
                        val yReflectionOfSecondCP =
                                2 * tempCurPoint.y - lastCommandSecondControlPointY // + curY
                        smooth.cp1 = PointF(xReflectionOfSecondCP, yReflectionOfSecondCP)
                    }
                    else
                    {
                        smooth.cp1 =
                                PointF(piecesPathsArr.last().knot.x, piecesPathsArr.last().knot.y)
                    }

                    tempCurPoint = smooth.knot
                    piecesPathsArr.add(smooth)
                }
                'Q' ->
                {
                    val str =
                            piece.replace("Q", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val quad = PathPiece(PathType.Quad)
                    quad.cp1 = PointF(points[0].toFloat(), points[1].toFloat())
                    quad.knot = PointF(points[2].toFloat(), points[3].toFloat())

                    tempCurPoint = quad.knot
                    piecesPathsArr.add(quad)
                }
                'q' ->
                {
                    val str =
                            piece.replace("q", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val quad = PathPiece(PathType.Quad)
                    //                quad.x1 = points[0].toFloat()
                    //                quad.y1 = points[1].toFloat()
                    quad.cp1 =
                            PointF(points[0].toFloat() + tempCurPoint.x,
                                   points[1].toFloat() + tempCurPoint.y)

                    //                quad.x = points[2].toFloat()
                    //                quad.y = points[3].toFloat()
                    quad.knot =
                            PointF(points[2].toFloat() + tempCurPoint.x,
                                   points[3].toFloat() + tempCurPoint.y)

                    tempCurPoint = quad.knot
                    piecesPathsArr.add(quad)
                }
                'T' ->
                {
                    val str =
                            piece.replace("T", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val smoothQ = PathPiece(PathType.SmoothQuad)
                    smoothQ.knot = PointF(points[0].toFloat(), points[1].toFloat())

                    /**
                     * T
                     * Draws a quadratic Bézier curve from the current point to (x,y).
                     * The control point is assumed to be the reflection of the control point
                     * on the previous command relative to the current point.
                     * If there is no previous command or if the previous command was not a Q, q, T or t,
                     * assume the control point is coincident with the current point
                     */

                    if (piecesPathsArr.count() == 0)
                    {
                        smoothQ.cp1 = PointF()
                    }
                    else if (piecesPathsArr.last().type == PathType.Quad || piecesPathsArr.last().type == PathType.SmoothQuad)
                    {
                        val curX = piecesPathsArr.last().knot.x
                        val curY = piecesPathsArr.last().knot.y
                        val lastCommandFirstControlPointX = piecesPathsArr.last().cp1.x
                        val lastCommandFirstControlPointY = piecesPathsArr.last().cp1.y

                        val xReflectionOfFirstCP = curX - lastCommandFirstControlPointX + curX
                        val yReflectionOfFirstCP = curY - lastCommandFirstControlPointY + curY

                        smoothQ.cp1 = PointF(xReflectionOfFirstCP, yReflectionOfFirstCP)
                    }
                    else
                    {
                        smoothQ.cp1 =
                                PointF(piecesPathsArr.last().knot.x, piecesPathsArr.last().knot.y)
                    }

                    tempCurPoint = smoothQ.knot
                    piecesPathsArr.add(smoothQ)
                }
                't' ->
                {
                    val str =
                            piece.replace("t", "")
                                    .replace(" ", ",")
                    val points = str.split(",")
                    val smoothQ = PathPiece(PathType.SmoothQuad)
                    smoothQ.knot =
                            PointF(points[0].toFloat() + tempCurPoint.x,
                                   points[1].toFloat() + tempCurPoint.y)

                    /**
                     * T
                     * Draws a quadratic Bézier curve from the current point to (x,y).
                     * The control point is assumed to be the reflection of the control point
                     * on the previous command relative to the current point.
                     * If there is no previous command or if the previous command was not a Q, q, T or t,
                     * assume the control point is coincident with the current point
                     */

                    if (piecesPathsArr.count() == 0)
                    {
                        smoothQ.cp1 = PointF()
                    }
                    else if (piecesPathsArr.last().type == PathType.Quad || piecesPathsArr.last().type == PathType.SmoothQuad)
                    {
                        val curX = piecesPathsArr.last().knot.x
                        val curY = piecesPathsArr.last().knot.y
                        val lastCommandFirstControlPointX = piecesPathsArr.last().cp1.x
                        val lastCommandFirstControlPointY = piecesPathsArr.last().cp1.y

                        val xReflectionOfFirstCP =
                                2 * tempCurPoint.x - lastCommandFirstControlPointX //+ curX
                        val yReflectionOfFirstCP =
                                2 * tempCurPoint.y - lastCommandFirstControlPointY //+ curY

                        smoothQ.cp1 = PointF(xReflectionOfFirstCP, yReflectionOfFirstCP)
                    }
                    else
                    {
                        smoothQ.cp1 =
                                PointF(piecesPathsArr.last().knot.x, piecesPathsArr.last().knot.y)
                    }

                    tempCurPoint = smoothQ.knot
                    piecesPathsArr.add(smoothQ)
                }
            }
        }

        thisPathPolygon.pieces = piecesPathsArr

        thisPathPolygon.fillColor = Color.parseColor(fillColorStr)
        thisPathPolygon.strokeColor = Color.parseColor(strokeColorStr)
        thisPathPolygon.strokeWidth = strokeWidthStr.toFloat()

        when (strokeLineCapStr)
        {
            "round" ->
            {
                thisPathPolygon.strokeLineCap = Paint.Cap.ROUND
            }
            "butt" ->
            {
                thisPathPolygon.strokeLineCap = Paint.Cap.BUTT
            }
            "square" ->
            {
                thisPathPolygon.strokeLineCap = Paint.Cap.SQUARE
            }

        }

        thisPathPolygon.closed = closed

        // TODO:
        // decide about this
        if (fillTypeStr == "nonZero") thisPathPolygon.fillType = Path.FillType.INVERSE_EVEN_ODD

        pathPolygons.add(thisPathPolygon)
    }

    return pathPolygons
}



