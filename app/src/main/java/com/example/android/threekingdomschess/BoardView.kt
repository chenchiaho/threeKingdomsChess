package com.example.android.threekingdomschess

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class BoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val originalY = 150f
    private val originalX = 200f
    private val rectDimen = 170f
    private val imageId = setOf(
            R.drawable.chess_k1, R.drawable.chess_k2,
            R.drawable.chess_p1, R.drawable.chess_p2,
            R.drawable.chess_g1, R.drawable.chess_g2,
            R.drawable.chess_b1, R.drawable.chess_b2,
            R.drawable.chess_r1, R.drawable.chess_r2,
            R.drawable.chess_h1, R.drawable.chess_h2,
            R.drawable.chess_c1, R.drawable.chess_c2
    )
    val bitmaps = mutableMapOf<Int, Bitmap>()
    val paint = Paint()

    init {
        decodeBitmap()
    }

    override fun onDraw(canvas: Canvas?) {
        drawBoard(canvas)
        drawPieces(canvas)
    }

    private fun decodeBitmap() {
        imageId.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    private fun drawPieces(canvas: Canvas?) {
        val chessModel = ChessModel()
        chessModel.reset()

        for (row in 0..4) {
            for (col in 0..8) {
                // let = skip null, run non-null
                chessModel.piecePosition(col, row)?.let { drawPiecesAt(canvas, col, row, it.resId) }
            }
        }
    }

    private fun drawPiecesAt(canvas: Canvas?, col: Int, row: Int, resId: Int) {
        val bitmap = bitmaps[resId]!!
        canvas?.drawBitmap(bitmap, null, RectF(
                originalX + col * rectDimen,
                originalY + (row) * rectDimen,
                originalX + (col +1) * rectDimen,
                originalY + (row + 1) * rectDimen), paint)
    }

    private fun drawBoard(canvas: Canvas?) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 11f
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
