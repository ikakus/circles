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
    val lightRed2 = Paint()

    val paintPen = Paint()


    val colors = intArrayOf(Color.RED, Color.BLUE)
    val positions = floatArrayOf(0f, 180f / 360f)

    val gradient =
        SweepGradient((width / 2).toFloat(), (measuredHeight / 2).toFloat(), colors, positions)

    lateinit var point: Point
    lateinit var point2: Point
    lateinit var rectF2: RectF
    lateinit var rectF: RectF


    val color = Color.DKGRAY
    val color2 = Color.DKGRAY

    init {
        lightRed.isAntiAlias = true
        lightRed.color = color

        lightRed2.isAntiAlias = true
        lightRed2.color = color2

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

        rectF2 = getRectForCircle(point2, 200)
        rectF = getRectForCircle(point, 100)
//        canvas.drawLine(
//            point.x.toFloat(),
//            point.y.toFloat(),
//            point2.x.toFloat(),
//            point2.y.toFloat(),
//            paintPen
//        )
        canvas.drawArc(rectF, 0f, 360f, true, lightRed)
        canvas.drawArc(rectF2, 0f, 360f, true, lightRed2)
    }

    var drag = false

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                val r = getRectForCircle(point2, 100)
                if (r.contains(event.x, event.y)) {
                    drag = true
                    point = Point((event.x).toInt(), (event.y).toInt())
                    point2
                    lightRed2.color = Color.RED
                }

            }
            event.action == MotionEvent.ACTION_MOVE -> {
                if (drag) {
                    point = Point((event.x).toInt(), (event.y).toInt())
                }

            }
            event.action == MotionEvent.ACTION_UP -> {
                ret(point)
                drag = false
                lightRed2.color = Color.DKGRAY

            }
        }
        invalidate()
        return true
    }

    fun ret(point: Point) {

        val durationMillis = 200L

        val factor = 1f

        val fromY = point.y
        val toY = height / 2
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
        val toX = width / 2
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