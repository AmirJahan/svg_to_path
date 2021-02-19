package com.oddinstitute.temp

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.atan2


class SimpleDraw: View
{
    lateinit var path: Path
    lateinit var paint: Paint

        constructor(context: Context?) : super(context)
        {
            path = Path()
            paint = Paint()
        }


        override fun onDraw(canvas: Canvas)
        {
            var startPoint = PointF(1000f, 100f)
            var endPoint = PointF(200f, 100f)

            path.moveTo(startPoint.x, startPoint.y)

            val rect = RectF (0f, 100f, 2000f, 1000f)


            path.addOval(rect, Path.Direction.CW)
            paint.style = Paint.Style.FILL
            paint.color = Color.RED
            canvas.drawPath(path, paint)

            path = Path()
            path.moveTo(startPoint.x, startPoint.y)
//            path.arcTo(rect, 0f, 180f, false)
//            path.arcTo(rect, 0f, 180f)
            path.arcTo(rect.left, rect.top, rect.right, rect.bottom, 0f, 180f, false)
            paint.color = Color.BLACK
            canvas.drawPath(path, paint)



            path = Path()
            path.moveTo(startPoint.x, startPoint.y)

            paint.color = Color.YELLOW
            val radiusX = 75f
            val radiusY = 125f
            val oval = RectF()
            oval.set(startPoint.x - radiusX,
                startPoint.y - radiusY,
                startPoint.x + radiusX,
                startPoint.y + radiusY)

            //       a75,125 1,1 0,200 0


            // A 100 100 0 0 0 450 250
            val val1 = 0
            val val2 = 0
            val val3 = 0



            val startAngle =
                (180 / Math.PI * atan2(endPoint.y.toDouble() - startPoint.y,
                    endPoint.x.toDouble() - startPoint.x))
//
//            path.arcTo(oval, startAngle.toFloat(), -sweepAngle as Float, true)



            canvas.drawPath(path, paint)



        }
    }