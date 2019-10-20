package com.example.circles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

open class CirclesView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val lightRed = Paint()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val colors = intArrayOf(Color.RED, Color.BLUE)
        val positions = floatArrayOf(0f, 180f / 360f)

        val gradient =
            SweepGradient((width / 2).toFloat(), (measuredHeight / 2).toFloat(), colors, positions)

        lightRed.color = Color.DKGRAY

        val point = Point()
        point.set(width/2, height/2)
        val rectF = getRectForCircle(point, 200)
        canvas.drawArc(rectF, 0f, 360f, true, lightRed)
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