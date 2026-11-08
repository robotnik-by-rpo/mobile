package com.example.anew

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.AsyncTask
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

data class Snowflake(
    var x: Float, var y: Float,
    val velocity: Float, val radius: Float, val color: Int,
    val phase: Float, val amplitude: Float
)

open class Snowflakes(ctx: Context) : View(ctx) {
    private lateinit var moveTask: MoveTask
    private var snow: Array<Snowflake> = emptyArray()
    private val paint = Paint()
    private var h = 1000; private var w = 1000

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLUE)
        for (s in snow) {
            paint.color = s.color
            canvas.drawCircle(s.x, s.y, s.radius, paint)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        h = b - t; w = r - l
        val rnd = Random(System.currentTimeMillis())
        snow = Array(60) {
            Snowflake(
                x = rnd.nextFloat() * w,
                y = rnd.nextFloat() * h,
                velocity = 15 + 10 * rnd.nextFloat(),
                radius = 5 + 10 * rnd.nextFloat(),
                color = Color.rgb(180 + rnd.nextInt(76), 200 + rnd.nextInt(56), 255),
                phase = rnd.nextFloat() * 2f * Math.PI.toFloat(),
                amplitude = 10f + 20f * rnd.nextFloat()
            )
        }
    }

    fun moveSnowflakes() {
        val time = System.currentTimeMillis() / 1000f
        for (s in snow) {
            val slowdown = 1f - 0.85f * (s.y / h)
            s.y += s.velocity * slowdown
            s.x += Math.sin((time + s.phase).toDouble()).toFloat() * s.amplitude * 0.02f
            if (s.x < 0) s.x += w
            if (s.x > w) s.x -= w
            if (s.y > h) s.y -= h
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (!::moveTask.isInitialized || moveTask.isCancelled) {
                moveTask = MoveTask(this); moveTask.execute(50)
            }
        }
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (::moveTask.isInitialized) moveTask.stop()
    }

    class MoveTask(private val s: Snowflakes) : AsyncTask<Int, Int, Int>() {
        @Volatile private var running = true
        fun stop() { running = false }
        override fun doInBackground(vararg p: Int?): Int {
            val delay = (p.getOrNull(0) ?: 50).toLong()
            while (running && !isCancelled) { Thread.sleep(delay); publishProgress(0) }
            return 0
        }
        override fun onProgressUpdate(vararg v: Int?) { s.moveSnowflakes() }
    }
}