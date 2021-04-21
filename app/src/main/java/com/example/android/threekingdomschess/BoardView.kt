package com.example.android.threekingdomschess

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class BoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val originalY = 85f
    private val originalX = 200f
    private val rectDimen = 180f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        for (i in 0..8) {
            for (j in 0..4) {
                canvas?.drawRect(
                    originalX + i * rectDimen,
                    originalY + j * rectDimen,
                    originalX + (i + 1) * rectDimen,
                    originalY + (j + 1) * rectDimen, paint)
            }
        }
    }
}