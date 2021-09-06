package com.example.android.threekingdomschess

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.alpha
import androidx.core.view.isVisible
import com.example.android.threekingdomschess.databinding.FragmentMainBinding
import com.example.android.threekingdomschess.model.Player
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.pieces.ChessPiece
import kotlinx.android.synthetic.main.fragment_main.view.*


class BoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val originalY = 60f
    private val originalX = 250f
    private val rectDimen = 160f
    private val imageId = setOf(
            R.drawable.g_general_w, R.drawable.g_pawn_w,
            R.drawable.b_guard_w, R.drawable.r_guard_w,
            R.drawable.b_horse_w, R.drawable.r_horse_w,
            R.drawable.b_rook_w, R.drawable.r_rook_w,
            R.drawable.b_cannon_w, R.drawable.r_cannon_w,
            R.drawable.b_elephant_w, R.drawable.r_elephant_w,
//            R.drawable.select_square, R.drawable.chess_back
            R.drawable.g_general_c1, R.drawable.g_general_c2,
            R.drawable.g_pawn_c1, R.drawable.g_pawn_c2,
            R.drawable.b_guard_c, R.drawable.r_guard_c,
            R.drawable.b_horse_c, R.drawable.r_horse_c,
            R.drawable.b_rook_c, R.drawable.r_rook_c,
            R.drawable.b_cannon_c, R.drawable.r_cannon_c,
            R.drawable.b_elephant_c, R.drawable.r_elephant_c
    )
    private val bitmaps = mutableMapOf<Int, Bitmap>()
    private var paint = Paint()
    private var movingPiece: ChessPiece? = null
    private var fromCol: Int = -1
    private var fromRow: Int = -1

    var chessDelegate: ChessDelegate? = null

    init {
        decodeBitmap()
    }

    override fun onDraw(canvas: Canvas?) {
        drawBoard(canvas)
        drawPieces(canvas)
    }

    fun onFirstTouchEvent(event: MotionEvent?) {

            fromCol = ((event!!.x - originalX) / rectDimen).toInt()
            fromRow = ((event.y - originalY) / rectDimen).toInt()
            chessDelegate?.assignPiecePosition(Square(fromCol, fromRow))?.let {
                movingPiece = it
            }

    }

    fun onSecondTouchEvent(event: MotionEvent?) {

            val col = ((event!!.x - originalX) / rectDimen).toInt()
            val row = ((event.y - originalY) / rectDimen).toInt()
            if (fromCol != col || fromRow != row) {

                chessDelegate?.movePiece(Square(fromCol, fromRow), Square(col, row))
                invalidate()
            }
            movingPiece = null

    }



    private fun decodeBitmap() {
        imageId.forEach {
            bitmaps[it] = BitmapFactory.decodeResource(resources, it)

        }
    }

    private fun drawPieces(canvas: Canvas?) {

        for (row in 0..4) {
            for (col in 0..8) {

                    chessDelegate?.assignPiecePosition(Square(col, row))?.let {
                        if (it != movingPiece) {
                            drawPiecesAt(canvas, col, row, it.resId)
                    }
                }
            }
        }

    }

    private fun drawPiecesAt(canvas: Canvas?, col: Int, row: Int, resId: Int) {
        val bitmap = bitmaps[resId]!!

        canvas?.drawBitmap(bitmap, null, RectF(
                originalX + col * rectDimen,
                originalY + row * rectDimen,
                originalX + (col + 1) * rectDimen,
                originalY + (row + 1) * rectDimen), paint)
    }

    private fun drawBoard(canvas: Canvas?) {

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        board(canvas)

        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.strokeJoin = Paint.Join.ROUND
        board(canvas)
    }

    private fun board (canvas: Canvas?) {
        for (i in 0..8) {
            for (j in 0..4) {
                canvas?.drawRoundRect(
                        originalX + i * rectDimen,
                        originalY + j * rectDimen,
                        originalX + (i + 1) * rectDimen,
                        originalY + (j + 1) * rectDimen, 10f, 10f, paint)
            }
        }
    }
}
