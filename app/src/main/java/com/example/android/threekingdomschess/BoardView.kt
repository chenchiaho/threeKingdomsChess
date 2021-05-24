package com.example.android.threekingdomschess

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.media.Image
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


private val TAG = "onTouchEvent"
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
            R.drawable.chess_c1, R.drawable.chess_c2,
            R.drawable.select_square
    )
    private val bitmaps = mutableMapOf<Int, Bitmap>()
    private val paint = Paint()
    private var movingPieceBitmap: Bitmap? = null
    private var movingPiece: ChessPiece? = null
    private var fromCol: Int = -1
    private var fromRow: Int = -1
    private var movingPieceX = -1f
    private var movingPieceY = -1f
    private var selectAndMove = false

    var chessDelegate: ChessDelegate? = null

    init {
        decodeBitmap()
    }

    override fun onDraw(canvas: Canvas?) {
        drawBoard(canvas)
        drawPieces(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false

        if (!selectAndMove && event.action == MotionEvent.ACTION_UP) {

            fromCol = ((event.x - originalX) / rectDimen).toInt()
            fromRow = ((event.y - originalY) / rectDimen).toInt()
            chessDelegate?.piecePosition(Square(fromCol, fromRow))?.let {
                movingPiece = it
                movingPieceBitmap = bitmaps[it.resId]

            }

            movingPieceBitmap = bitmaps[setBackgroundResource(R.drawable.select_square)]

            selectAndMove = true
            Log.d(TAG, "Button Press activated")
            return true

        }

        if (selectAndMove && event.action == MotionEvent.ACTION_UP) {
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    fromCol = ((event.x - originalX) / rectDimen).toInt()
//                    fromRow = ((event.y - originalY) / rectDimen).toInt()
//                    chessDelegate?.piecePosition(Square(fromCol, fromRow))?.let {
//                        movingPiece = it
//                        movingPieceBitmap = bitmaps[it.resId]
//                    }
//                }
//
//                MotionEvent.ACTION_MOVE -> {
//                    movingPieceX = event.x
//                    movingPieceY = event.y
//                    invalidate()
//                }

            val col = ((event.x - originalX) / rectDimen).toInt()
            val row = ((event.y - originalY) / rectDimen).toInt()
            if (fromCol != col || fromRow != row) {

                chessDelegate?.movePiece(Square(fromCol, fromRow),Square(col, row))
                invalidate()
            }
            movingPiece = null
            movingPieceBitmap = null
            movingPieceBitmap = bitmaps[setBackgroundColor(0)]

            selectAndMove = false

            return true
         }
        return true
    }


    private fun decodeBitmap() {
        imageId.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    private fun drawPieces(canvas: Canvas?) {

        for (row in 0..4) {
            for (col in 0..8) {

                    chessDelegate?.piecePosition(Square(col, row))?.let {
                        if (it != movingPiece) {
                            drawPiecesAt(canvas, col, row, it.resId)
                    }
                }
            }
        }

        movingPieceBitmap?.let {
            canvas?.drawBitmap(it, null, RectF(
                    movingPieceX - rectDimen * 0.8f,
                    movingPieceY - (rectDimen * 1.5f),
                    movingPieceX + (rectDimen * 0.8f),
                    movingPieceY + rectDimen * 0.1f), paint)
        }
    }

    private fun drawPiecesAt(canvas: Canvas?, col: Int, row: Int, resId: Int) {
        val bitmap = bitmaps[resId]!!
        canvas?.drawBitmap(bitmap, null, RectF(
                originalX + col * rectDimen,
                originalY + (row) * rectDimen,
                originalX + (col + 1) * rectDimen,
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
