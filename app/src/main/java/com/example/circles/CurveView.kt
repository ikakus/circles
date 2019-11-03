package com.example.circles

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
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
    val an2 = ValueAnimator.ofFloat(0f, 360f)

    var h = 100f

    init {
        borderPathPaint.apply {
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.FILL
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
            path.transform(mMatrix)

            invalidate()
        }
//        an.start()

        an2.duration = 20000

        an2.interpolator = LinearInterpolator()
        an2.repeatCount = INFINITE
        an2.addUpdateListener {
//            h = it.animatedValue as Float
            val mMatrix = Matrix()

            val bounds = RectF()
            path.computeBounds(bounds, true)
            mMatrix.postRotate(it.animatedValue as Float, bounds.centerX(), bounds.centerY())
//            angle = (it.animatedValue as Float).toDouble()
            path.transform(mMatrix)
            invalidate()
        }
//        an2.start()

    }

    val mMatrix = Matrix()
    var w = 200f
    var center = PointF((width / 2).toFloat(), 0f)
    var angle: Double = 0.0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        center = PointF((width / 2).toFloat(), (height / 2).toFloat())

    }

    override fun onDraw(canvas: Canvas) {

        path.reset()

        points(path, center, h, w)

//        mMatrix.
//        mMatrix.postRotate(angle.toFloat())
//        mMatrix.postTranslate((width / 2).toFloat(), (height / 2).toFloat())

//        path.transform(mMatrix)
        canvas.drawPath(path, borderPathPaint)
//        drawCircles(canvas, listOf(startPoint, cPoint1, cPoint2, endPoint))

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                h = event.y
//                center.x = event.x


            }
            event.action == MotionEvent.ACTION_MOVE -> {
                h = event.y
//                center.x = event.x
            }
            event.action == MotionEvent.ACTION_UP -> {

                h = 100f
            }
        }
        invalidate()
        return true
    }

    fun angle(center: PointF, point: PointF): Double {
        return Math.atan2(
            (point.y - center.y).toDouble(),
            (point.x - center.x).toDouble()
        ) * 180 / Math.PI;

    }

    fun points(path: Path, center: PointF, heigt: Float, width: Float = 200f) {
        val startX = center.x - width / 2
        val startY = center.y
        val step = (width) / 4
        // 1
        val startPoint = PointF(startX, startY)
        val cPoint1 = PointF(startX + step, startY)
        val cPoint2 = PointF(startX + step, heigt)
        val endPoint = PointF(startX + step * 2, heigt)

        //2
        val cPoint3 = PointF(startX + step * 3, heigt)
        val cPoint4 = PointF(startX + step * 3, startY)
        val endPoint2 = PointF(startX + step * 4, startY)

        path.moveTo(startPoint.x, startPoint.y)
        path.cubicTo(
            cPoint1.x,
            cPoint1.y,

            cPoint2.x,
            cPoint2.y,

            endPoint.x,
            endPoint.y
        )

        path.cubicTo(
            cPoint3.x,
            cPoint3.y,

            cPoint4.x,
            cPoint4.y,

            endPoint2.x,
            endPoint2.y
        )
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