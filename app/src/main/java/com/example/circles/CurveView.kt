package com.example.circles

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class CurveView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

//    private val borderPath = Path()
//    private val points = mutableListOf<PointF>()
//    private val conPoint1 = mutableListOf<PointF>()
//    private val conPoint2 = mutableListOf<PointF>()

    private val path = Path()

    private val borderPathPaint = Paint()

    val an = ValueAnimator.ofFloat(0f, 360f)

    init {
        borderPathPaint.apply {
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.STROKE
            color = Color.RED
        }

        an.duration = 2000

        an.interpolator = LinearInterpolator()
        an.repeatCount = INFINITE
        an.addUpdateListener {
            val value = it.animatedValue as Float

            path.reset()
            path.cubicTo(600f, 0f, 0f, 600f, 600f, 600f)
//            borderPath.set(path)

            val mMatrix = Matrix()
            val bounds = RectF()
            path.computeBounds(bounds, true)
            mMatrix.postRotate(value, bounds.centerX(), bounds.centerY())
//            borderPath.transform(mMatrix)
            path.transform(mMatrix)

            invalidate()
        }
//        an.start()

    }

    override fun onDraw(canvas: Canvas) {

        path.reset()
//        path.cubicTo(600f, 0f, 0f, 600f, 600f, 600f)
        val startPoint = PointF(10f, 10f)
        val cPoint1 = PointF(300f, 0f)
        val cPoint2 = PointF(300f, 600f)
        val endPoint = PointF(600f, 600f)

        path.moveTo(startPoint.x, startPoint.y)
        path.cubicTo(
            cPoint1.x,
            cPoint1.y,

            cPoint2.x,
            cPoint2.y,

            endPoint.x,
            endPoint.y
        )

        canvas.drawPath(path, borderPathPaint)
        drawCircles(canvas, listOf(startPoint, cPoint1, cPoint2, endPoint))

    }

    fun drawCircles(canvas: Canvas, points: List<PointF>) {
        val paint = Paint()
        paint.apply {
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.STROKE
            color = Color.BLUE
        }
        points.forEach {
            drawCircle(canvas, it, 10f, paint)
        }
    }

    fun drawCircle(canvas: Canvas, point: PointF, radius: Float, paint: Paint) {
        canvas.drawCircle(
            point.x,
            point.y,
            radius,
            paint
        )

    }
}