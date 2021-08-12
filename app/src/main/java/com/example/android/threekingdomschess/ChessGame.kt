package com.example.android.threekingdomschess

import android.util.Log
import com.example.android.threekingdomschess.model.Player
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.pieces.ChessPiece
import com.example.android.threekingdomschess.pieces.ChessType
import com.example.android.threekingdomschess.pieces.PieceLogic
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


object ChessGame {

    private val pieceLogic = PieceLogic()
    private var pieceSet = mutableSetOf<ChessPiece>()

    init {
        initPosition()
        reset()
        }

    private fun clear() {
        pieceSet.clear()
    }

    private fun addPiece(piece: ChessPiece) {
        pieceSet.add(piece)
    }

    private fun legalMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row){
            return false
        } else if (to.col < 0 || to.col > 8 || to.row < 0 || to.row > 4)
            return false
        val legalMove = piecePosition(from) ?: return false
        return when(legalMove.cType) {
            ChessType.HORSE -> pieceLogic.horseLegal(from, to)
            ChessType.ROOK -> pieceLogic.rookLegal(from, to)
            ChessType.KING -> pieceLogic.rookLegal(from, to)
            ChessType.ADVISER -> pieceLogic.adviserLegal(from, to)
            ChessType.PAWN -> pieceLogic.pawnLegal(from, to)
            ChessType.GUARD -> pieceLogic.guardLegal(from, to)
            ChessType.CANNON -> pieceLogic.cannonLegal(from, to)
        }
    }

    fun movePiece(from: Square, to: Square) {
        if (legalMove(from, to)) {
        movePiece(from.col, from.row, to.col, to.row)
        }
    }

    private fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        //?: = if operand1 is null, run operand2
        if (fromCol == toCol && fromRow == toRow) return

        val movingPiece = piecePosition(fromCol, fromRow) ?: return

        piecePosition(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                return
            }
            pieceSet.remove(it)
        }


//        val animate = TranslateAnimation(R.drawable.chess_k1.getX, toCol-fromCol.toFloat(), movingPiece.row.toFloat(), toRow-fromRow.toFloat())
//        animate.duration = 1000
//        animate.start()

        pieceSet.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow))
    }

    fun reset() {
        val position = initPosition()
        clear()

        for (i in 0..31) {
            when(i) {
                0 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING, R.drawable.chess_k1))
                1 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING, R.drawable.chess_k2))
                in 2..6 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN, R.drawable.chess_p1))
                in 7..11 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN, R.drawable.chess_p2))

                12, 13 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.GUARD, R.drawable.chess_g1))
                14, 15 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
                16, 17 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.HORSE, R.drawable.chess_h1))
                18, 19 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.ROOK, R.drawable.chess_r1))
                20, 21 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.CANNON, R.drawable.chess_c1))

                22, 23 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.GUARD, R.drawable.chess_g2))
                24, 25 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ADVISER, R.drawable.chess_b2))
                26, 27 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.HORSE, R.drawable.chess_h2))
                28, 29 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ROOK, R.drawable.chess_r2))
                30, 31 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.CANNON, R.drawable.chess_c2))

            }
        }
    }

    private fun initPosition(): MutableList<Square> {

        val randomPiece = mutableListOf<Square>()

        (0..4).filter { it !=2 }.forEach { row ->
            (0..8).filter { it != 4 }.forEach { col ->

                randomPiece.add(Square(col, row))
            }
        }

        randomPiece.shuffle()
        return randomPiece
    }

    fun piecePosition(square: Square): ChessPiece? {
        return piecePosition(square.col, square.row)
    }

    fun piecePosition(col: Int, row: Int): ChessPiece? {
        for (piece in pieceSet) {
            if (col == piece.col && row == piece.row) {
                return piece
            }
        }
        return null
    }

}

