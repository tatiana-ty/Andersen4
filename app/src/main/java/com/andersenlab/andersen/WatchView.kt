package com.andersenlab.andersen

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.time.LocalTime
import kotlin.math.min

class WatchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintCircle = Paint()
    private val paintHand = Paint()

    private var radius = 0.0f
    private val watchColor: Int
    private val strokeColor: Int
    private val handHourColor: Int
    private val handMinuteColor: Int
    private val handSecondColor: Int
    private val handHourWidth: Float
    private val handMinuteWidth: Float
    private val handSecondWidth: Float
    private val strokeWidth: Float

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WatchView)
        watchColor = typedArray.getColor(R.styleable.WatchView_watchColor, Color.WHITE)
        strokeColor = typedArray.getColor(R.styleable.WatchView_strokeColor, Color.BLACK)
        handHourColor = typedArray.getColor(R.styleable.WatchView_handHourColor, Color.BLACK)
        handMinuteColor = typedArray.getColor(R.styleable.WatchView_handMinuteColor, Color.BLACK)
        handSecondColor = typedArray.getColor(R.styleable.WatchView_handSecondColor, Color.RED)
        handHourWidth = typedArray.getDimension(R.styleable.WatchView_handHourWidth, 20f)
        handMinuteWidth = typedArray.getDimension(R.styleable.WatchView_handMinuteWidth, 15f)
        handSecondWidth = typedArray.getDimension(R.styleable.WatchView_handSecondWidth, 10f)
        strokeWidth = typedArray.getDimension(R.styleable.WatchView_strokeWidth, 15f)
        typedArray.recycle()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paintCircle.style = Paint.Style.FILL
        paintCircle.color = watchColor
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius,
            paintCircle
        )
        paintCircle.style = Paint.Style.STROKE
        paintCircle.color = strokeColor
        paintCircle.strokeWidth = strokeWidth
        canvas.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            radius,
            paintCircle
        )
        for (i in 1..12) {
            canvas.drawLine(
                (width / 2).toFloat(),
                (height / 2).toFloat() - radius + 50f,
                (width / 2).toFloat(),
                (height / 2) - radius,
                paintCircle
            )
            canvas.rotate(
                (360 / 12).toFloat(),
                (width / 2).toFloat(),
                (height / 2).toFloat()
            )
        }

        val time = LocalTime.now()
        val hours = time.hour
        val minutes = time.minute
        val seconds = time.second

        paintHand.strokeWidth = handHourWidth
        paintHand.color = handHourColor
        val hourRotation = (360 / 60).toFloat() * (30 + hours)
        canvas.rotate(
            hourRotation,
            (width / 2).toFloat(),
            (height / 2).toFloat()
        )
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat() + 250f,
            paintHand
        )
        paintHand.strokeWidth = handMinuteWidth
        paintHand.color = handMinuteColor
        val minutesRotation = (360 / 60).toFloat() * (30 + minutes) - hourRotation
        canvas.rotate(
            minutesRotation,
            (width / 2).toFloat(),
            (height / 2).toFloat()
        )
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat() + 300f,
            paintHand
        )
        paintHand.strokeWidth = handSecondWidth
        paintHand.color = handSecondColor
        canvas.rotate(
            (360 / 60).toFloat() * (30 + seconds) - minutesRotation - hourRotation,
            (width / 2).toFloat(), (height / 2).toFloat()
        )
        canvas.drawLine(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (width / 2).toFloat(),
            (height / 2).toFloat() + 300f,
            paintHand
        )

        invalidate()
    }

}