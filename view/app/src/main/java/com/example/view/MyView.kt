package com.example.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*

class MyView(context: Context?) : View(context) {
    val p = Paint(); val r = Random()
    var layoutWidth = -1; var layoutHeight = -1

    val tiles = Array(4) { BooleanArray(4) { r.nextBoolean() } }
    val padding = 10
    lateinit var tilesRect: Array<Array<Rect>>
    var centerX = 50f
    val rect = Rect(0.1f, 0.1f, 0.1f, 0.1f)
    var centerY = 50f
    var w = 1000
    var h = 1000


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        layoutWidth = right - left
        layoutHeight = bottom - top
        Log.d("SizeCheck", "onLayout: width=$layoutWidth, height=$layoutHeight (unit: px)")
    }

    override fun onDraw(canvas: Canvas) {
        p.setColor(Color.RED)

        Log.d("mytag", "onDraw: w ${canvas.width}, h ${canvas.height}")
        Log.d("mytag", "onLayout: w ${layoutWidth}, h ${layoutHeight}")
        w = width; h = height

        canvas.drawColor(Color.YELLOW)
        canvas.drawColor(Color.YELLOW)
        canvas.drawCircle(centerX, centerY, 30f, p)
        canvas.drawRect(
            rect.x * width,
            rect.y * height,
            (rect.x + rect.width) * width,
            (rect.y + rect.height) * height,
            p
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            centerX = x; centerY = y
        }
        invalidate()

        return true
    }
}