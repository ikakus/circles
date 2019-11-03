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

    private val path = Path()
    private val borderPathPaint = Paint()

    val an2 = ValueAnimator.ofFloat(0f, 360f)

    var h = 100f

    var angle = 0f

    init {
        borderPathPaint.apply {
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.FILL
            color = Color.RED
        }

        an2.duration = 20000

        an2.interpolator = LinearInterpolator()
        an2.repeatCount = INFINITE
        an2.addUpdateListener {
            path.reset()
            val ang = it.animatedValue as Float
            val mMatrix = Matrix()
            val bounds = RectF()
            points(path, center, h, w)
            path.computeBounds(bounds, true)
            mMatrix.postRotate(ang, center.x, center.y)
            path.transform(mMatrix)
            invalidate()
        }

    }

    var w = 200f
    var center = PointF((width / 2).toFloat(), (height / 2).toFloat())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        center = PointF((width / 2).toFloat(), (height / 2).toFloat())
//        an2.start()
    }

    override fun onDraw(canvas: Canvas) {
        path.reset()
//        points(path, center, h, w)
        val mMatrix = Matrix()
        val bounds = RectF()
        points(path, center, h, w)
        path.computeBounds(bounds, true)
        mMatrix.postRotate(angle, center.x, center.y)
        path.transform(mMatrix)
        canvas.drawPath(path, borderPathPaint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val point = PointF(event.x, event.y)
        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                h = dist(center, point).toFloat()
//                center.x = event.x
                angle = angle(center, point).toFloat()

            }
            event.action == MotionEvent.ACTION_MOVE -> {
                h = dist(center, point).toFloat()
                angle = angle(center, point).toFloat()

//                center.x = event.x
            }
            event.action == MotionEvent.ACTION_UP -> {

//                h = 10f
//                angle = 0f
            }
        }
        invalidate()
        return true
    }

    fun angle(center: PointF, point: PointF): Double {
        val angle = Math.atan2(
            (point.y - center.y).toDouble(),
            (point.x - center.x).toDouble()
        ) * 180 / Math.PI;

        return angle + 90

    }

    fun dist(point1: PointF, point2: PointF): Double {
        var a = point1.x - point2.x;
        var b = point1.y - point2.y;

        return Math.sqrt((a * a + b * b).toDouble())
    }

    fun points(path: Path, center: PointF, h: Float, width: Float = 200f) {
        val heigt = h
        val startX = center.x - width / 2
        val startY = center.y
        val step = (width) / 4
        // 1
        val startPoint = PointF(startX, startY)
        val cPoint1 = PointF(startX + step, startY)
        val cPoint2 = PointF(startX + step, center.y - heigt)
        val endPoint = PointF(startX + step * 2, center.y - heigt)

        //2
        val cPoint3 = PointF(startX + step * 3, center.y - heigt)
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