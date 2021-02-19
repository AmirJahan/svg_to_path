package com.oddinstitute.temp

import android.content.Context
import android.graphics.*
import android.view.View
import com.oddinstitute.svgconvert.*


const val controlPointRadius = 10f
const val controlPointStrokeWidth = 5f
const val controlPointLinesStrokeWidth = 2f
const val controlPointsUnselectedColor = Color.YELLOW
const val controlPointsSelectedColor = Color.BLUE

const val controlLinesColor = Color.GRAY



class DrawView : View
{
    var mainPaint: Paint = Paint()
    var polygonDataArr: ArrayList<PolygonData> = arrayListOf()

    lateinit var path: Path

    constructor(context: Context?, polygonDataArray: ArrayList<PolygonData>) : super(context)
    {
        this.polygonDataArr = polygonDataArray
    }


    fun makeControlPoints (polyData: PolygonData)
    {


            path = Path()

        for (i in 0 until polyData.pathData.count()) {

            val piece = polyData.pathData[i]

            when (piece.type)
            {
                PathType.Curve, PathType.SmoothCurve -> {
                    // make Ctrl Points
                    path.addCircle(piece.cp1.x, piece.cp1.y, controlPointRadius, Path.Direction.CW)
                    path.addCircle(piece.cp2.x, piece.cp2.y, controlPointRadius, Path.Direction.CW)
                }

                PathType.Quad, PathType.SmoothQuad -> {
                    path.addCircle(piece.cp1.x, piece.cp1.y, controlPointRadius, Path.Direction.CW)
                }
            }
        }

    }

    fun makeControlPointsLines  (polyData: PolygonData)
    {
            path = Path()

            var curPoint = PointF()
            for (i in 0 until polyData.pathData.count()) {

                val piece = polyData.pathData[i]

                when (piece.type) {
                    // TODO: Add normalized vector to remove the parts
                    // under the circles
                    PathType.Curve, PathType.SmoothCurve -> {
                        // draw lines between Ctrl Points and the Knots
                        path.moveToPoint(curPoint)
                        path.lineToPoint(piece.cp1)
                        path.moveToPoint(piece.knot)
                        path.lineToPoint(piece.cp2)
                    }

                    PathType.Quad, PathType.SmoothQuad -> {
                        path.moveToPoint(curPoint)
                        path.lineToPoint(piece.cp1)
                        path.lineToPoint(piece.knot)
                    }
                }

                // remember current point
                curPoint = PointF(piece.knot.x, piece.knot.y)
            }
        }



    fun makePath (polyData: PolygonData)
    {

            path = Path()
            for (piece in polyData.pathData) {
                when (piece.type) {
                    PathType.Move -> {
//                    path.moveTo(piece.x, piece.y)
                        path.moveToPoint(piece.knot)
                    }
                    PathType.Line, PathType.Horizontal, PathType.Vertical -> {
//                    path.lineTo(piece.x, piece.y)
                        path.lineToPoint(piece.knot)
                    }
                    PathType.Curve, PathType.SmoothCurve -> {
//                    path.cubicTo(piece.x1, piece.y1, piece.x2, piece.y2, piece.x, piece.y)
                        path.cubicToCpCpPoint(piece.cp1, piece.cp2, piece.knot)
                    }
                    PathType.Quad, PathType.SmoothQuad -> {
//                    path.quadTo(piece.x1, piece.y1, piece.x, piece.y)
                        path.quadToCpPoint(piece.cp1, piece.knot)
                    }
                    PathType.Arc -> {
//                        path.arc
                    }
                }
            }

        if ( polyData.closed)
        path.close()

    }


    override fun onDraw(canvas: Canvas)
    {

        for (eachInternalPolygon in polygonDataArr)
        {
            makePath(eachInternalPolygon)

            // TODO: How to test if there is fill color
            // fill
            mainPaint.style = Paint.Style.FILL
            mainPaint.color = eachInternalPolygon.fillColor
            mainPaint.strokeCap = eachInternalPolygon.strokeLineCap
            canvas.drawPath(path, mainPaint)


            if (eachInternalPolygon.strokeWidth > 0) {
                // stroke
                mainPaint.style = Paint.Style.STROKE
                mainPaint.strokeWidth = eachInternalPolygon.strokeWidth
                mainPaint.color = eachInternalPolygon.strokeColor
                mainPaint.strokeCap = Paint.Cap.ROUND
                mainPaint.pathEffect = null

                canvas.drawPath(path, mainPaint)
            }



            // control points
            makeControlPoints(eachInternalPolygon)
            mainPaint.style = Paint.Style.STROKE
            mainPaint.strokeWidth = controlPointStrokeWidth
            mainPaint.color = controlPointsUnselectedColor
            mainPaint.strokeCap = Paint.Cap.ROUND
            canvas.drawPath(path, mainPaint)

            // control lines
            makeControlPointsLines(eachInternalPolygon)
            mainPaint.style = Paint.Style.STROKE
            mainPaint.strokeWidth = controlPointLinesStrokeWidth
            mainPaint.color = controlLinesColor
            mainPaint.strokeCap = Paint.Cap.ROUND
            mainPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0f)
            canvas.drawPath(path, mainPaint)

        }

    }
}
