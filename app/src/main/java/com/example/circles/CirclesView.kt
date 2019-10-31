package com.example.circles

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator

open class CirclesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val lightRed = Paint()

    val paintPen = Paint()


    val colors = intArrayOf(Color.RED, Color.BLUE)
    val positions = floatArrayOf(0f, 180f / 360f)

    val gradient =
        SweepGradient((width / 2).toFloat(), (measuredHeight / 2).toFloat(), colors, positions)

    var point = Point(width / 2, height / 2)
    var point2 = Point(width / 2, height / 2)

    val color = Color.DKGRAY

    init {
        lightRed.isAntiAlias = true
        lightRed.color = color
        paintPen.strokeWidth = 200f
        paintPen.isAntiAlias = true
        paintPen.color = color
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        point = Point(width / 2, height / 2)
        point2 = Point(width / 2, height / 2)

    }

    override fun onDraw(canvas: Canvas) {

        val rectF = getRectForCircle(point, 100)
        val rectF2 = getRectForCircle(point2, 200)
//        canvas.drawLine(
//            point.x.toFloat(),
//            point.y.toFloat(),
//            point2.x.toFloat(),
//            point2.y.toFloat(),
//            paintPen
//        )
        canvas.drawArc(rectF, 0f, 360f, true, lightRed)
        canvas.drawArc(rectF2, 0f, 360f, true, lightRed)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                point = Point((event.x).toInt(), (event.y).toInt())

            }
            event.action == MotionEvent.ACTION_MOVE -> {
                point = Point((event.x).toInt(), (event.y).toInt())

            }
            event.action == MotionEvent.ACTION_UP -> {
                ret(point)
            }
        }
        invalidate()
        return true
    }

    fun ret(point: Point) {

        val durationMillis = 500L

        val factor = 1f

        val fromY = point.y
        val toY = height /2
        ValueAnimator.ofInt(fromY, toY).apply {
            duration = durationMillis
            start()
            addUpdateListener { updatedAnimation ->
                point.y = updatedAnimation.animatedValue as Int
                invalidate()
            }
            interpolator = AccelerateInterpolator(factor)
        }

        val fromX = point.x
        val toX = width /2
        ValueAnimator.ofInt(fromX, toX).apply {
            duration = durationMillis
            start()
            addUpdateListener { updatedAnimation ->
                point.x = updatedAnimation.animatedValue as Int
                invalidate()
            }
            interpolator = AccelerateInterpolator(factor)

        }
    }
}



fun getRectForCircle(point: Point, radius: Int): RectF {

    return RectF(
        point.x.toFloat() - radius,
        point.y.toFloat() - radius,
        point.x.toFloat() + radius,
        point.y.toFloat() + radius
    )

}