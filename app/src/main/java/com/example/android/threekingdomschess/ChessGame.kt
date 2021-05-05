package com.example.android.threekingdomschess

import android.util.Log
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val TAG = "ChessGame"

object ChessGame {

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

    private fun horseLegal(from: Square, to: Square): Boolean {
        return abs(from.col - to.col) == 2 && abs(from.row - to.row) == 1 ||
                abs(from.col - to.col) == 1 && abs(from.row - to.row) == 2
    }

    private fun rookLegal(from: Square, to: Square): Boolean {
        if (from.col == to.col && rookVerticalPath(from, to) ||
            from.row == to.row && rookHorizontalPath(from, to)) {
            return true
        }
        return false
    }

    private fun adviserLegal(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == 2 &&
            abs(to.row - from.row) == 2) {
            return true
        } else if (abs(from.col - to.col) == 1 &&
                   abs(to.row - from.row) == 1) {
            return true
        }
        return false
    }

    private fun rookVerticalPath(from: Square, to: Square): Boolean {
        if (from.col != to.col) return false
        val gap = abs(from.row - to.row) -1
        if (gap == 0) return true
        for (i in 1..gap) {
            val nextRow = if (to.row > from.row) from.row + i else from.row -i
            if (piecePosition(Square(from.col, nextRow)) !=null) {
                return false
            }
        }
        return true
    }

    private fun rookHorizontalPath(from: Square, to: Square): Boolean {
        if (from.row != to.row) return false
        val gap = abs(from.col - to.col) -1
        if (gap == 0) return true
        for (i in 1..gap) {
            val nextCol = if (to.col > from.col) from.col + i else from.col -i
            if (piecePosition(Square(nextCol, from.row)) !=null) {
                return false
            }
        }
        return true
    }

    private fun pawnLegal(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == 1 &&
                abs(to.row - from.row) == 0) {
            return true
        } else if (abs(from.col - to.col) == 0 &&
                abs(to.row - from.row) == 1) {
            return true
        }
        return false
    }

    private fun guardLegal(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == 1 &&
                abs(to.row - from.row) == 1) {
            return true
        }
        return false
    }

    private fun piecesBetween(fromCol: Int, fromRow: Int,
                              toCol: Int, toRow: Int): Int {
        var count = 0
        val start: Int
        val end: Int
        if (fromCol == toCol) {
            start = min(fromRow, toRow)
            end = max(fromRow, toRow)
            for (row in start + 1 until end) {
                count += if (piecePosition(fromCol, row) == null) 0 else 1
            }
        } else {
            start = min(fromCol, toCol)
            end = max(fromCol, toCol)
            for (col in start + 1 until end) {
                count += if (piecePosition(col, fromRow) == null) 0 else 1
            }
        }
        return count
    }

    private fun cannonLegal(from: Square, to: Square): Boolean {
        return if (piecePosition(to.col, to.row) == null) {
            rookLegal(from, to)
        } else piecesBetween(from.col, from.row, to.col, to.row) == 1
    }

    private fun legalMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row){
            return false
        }
        val legalMove = piecePosition(from) ?: return false
        return when(legalMove.cType) {
            ChessType.HORSE -> horseLegal(from, to)
            ChessType.ROOK -> rookLegal(from, to)
            ChessType.KING1 -> rookLegal(from, to)
            ChessType.KING2 -> rookLegal(from, to)
            ChessType.ADVISER -> adviserLegal(from, to)
            ChessType.PAWN1 -> pawnLegal(from, to)
            ChessType.PAWN2 -> pawnLegal(from, to)
            ChessType.GUARD -> guardLegal(from, to)
            ChessType.CANNON -> cannonLegal(from, to)
        }
        return true
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
        pieceSet.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow))
    }

    private fun reset() {
        var position = initPosition()
        clear()

        addPiece(ChessPiece(position[0].col, position[0].row, Player.GREEN, ChessType.KING1, R.drawable.chess_k1))
        addPiece(ChessPiece(position[1].col, position[1].row, Player.GREEN, ChessType.KING2, R.drawable.chess_k2))
        addPiece(ChessPiece(position[2].col, position[2].row, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(position[3].col, position[3].row, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(position[4].col, position[4].row, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(position[5].col, position[5].row, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(position[6].col, position[6].row, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(position[7].col, position[7].row, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(position[8].col, position[8].row, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(position[9].col, position[9].row, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(position[10].col, position[10].row, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(position[11].col, position[11].row, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))

        addPiece(ChessPiece(position[12].col, position[12].row, Player.BLACK, ChessType.GUARD, R.drawable.chess_g1))
        addPiece(ChessPiece(position[13].col, position[13].row, Player.BLACK, ChessType.GUARD, R.drawable.chess_g1))
        addPiece(ChessPiece(position[14].col, position[14].row, Player.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
        addPiece(ChessPiece(position[15].col, position[15].row, Player.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
        addPiece(ChessPiece(position[16].col, position[16].row, Player.BLACK, ChessType.HORSE, R.drawable.chess_h1))
        addPiece(ChessPiece(position[17].col, position[17].row, Player.BLACK, ChessType.HORSE, R.drawable.chess_h1))
        addPiece(ChessPiece(position[18].col, position[18].row, Player.BLACK, ChessType.ROOK, R.drawable.chess_r1))
        addPiece(ChessPiece(position[19].col, position[19].row, Player.BLACK, ChessType.ROOK, R.drawable.chess_r1))
        addPiece(ChessPiece(position[20].col, position[20].row, Player.BLACK, ChessType.CANNON, R.drawable.chess_c1))
        addPiece(ChessPiece(position[21].col, position[21].row, Player.BLACK, ChessType.CANNON, R.drawable.chess_c1))

        addPiece(ChessPiece(position[22].col, position[22].row, Player.RED, ChessType.GUARD, R.drawable.chess_g2))
        addPiece(ChessPiece(position[23].col, position[23].row, Player.RED, ChessType.GUARD, R.drawable.chess_g2))
        addPiece(ChessPiece(position[24].col, position[24].row, Player.RED, ChessType.ADVISER, R.drawable.chess_b2))
        addPiece(ChessPiece(position[25].col, position[25].row, Player.RED, ChessType.ADVISER, R.drawable.chess_b2))
        addPiece(ChessPiece(position[26].col, position[26].row, Player.RED, ChessType.HORSE, R.drawable.chess_h2))
        addPiece(ChessPiece(position[27].col, position[27].row, Player.RED, ChessType.HORSE, R.drawable.chess_h2))
        addPiece(ChessPiece(position[28].col, position[28].row, Player.RED, ChessType.ROOK, R.drawable.chess_r2))
        addPiece(ChessPiece(position[29].col, position[29].row, Player.RED, ChessType.ROOK, R.drawable.chess_r2))
        addPiece(ChessPiece(position[30].col, position[30].row, Player.RED, ChessType.CANNON, R.drawable.chess_c2))
        addPiece(ChessPiece(position[31].col, position[31].row, Player.RED, ChessType.CANNON, R.drawable.chess_c2))
    }

    fun initPosition(): MutableList<Square> {

        var randomPiece = mutableListOf<Square>()
        randomPiece.add(Square(0, 4))
        randomPiece.add(Square(1, 4))
        randomPiece.add(Square(2, 4))
        randomPiece.add(Square(3, 4))
        randomPiece.add(Square(5, 4))
        randomPiece.add(Square(6, 4))
        randomPiece.add(Square(7, 4))
        randomPiece.add(Square(8, 4))
        randomPiece.add(Square(0, 3))
        randomPiece.add(Square(1, 3))
        randomPiece.add(Square(2, 3))
        randomPiece.add(Square(3, 3))
        randomPiece.add(Square(5, 3))
        randomPiece.add(Square(6, 3))
        randomPiece.add(Square(7, 3))
        randomPiece.add(Square(8, 3))
        randomPiece.add(Square(0, 1))
        randomPiece.add(Square(1, 1))
        randomPiece.add(Square(2, 1))
        randomPiece.add(Square(3, 1))
        randomPiece.add(Square(5, 1))
        randomPiece.add(Square(6, 1))
        randomPiece.add(Square(7, 1))
        randomPiece.add(Square(8, 1))
        randomPiece.add(Square(0, 0))
        randomPiece.add(Square(1, 0))
        randomPiece.add(Square(2, 0))
        randomPiece.add(Square(3, 0))
        randomPiece.add(Square(5, 0))
        randomPiece.add(Square(6, 0))
        randomPiece.add(Square(7, 0))
        randomPiece.add(Square(8, 0))
        Collections.shuffle(randomPiece)

        Log.d(TAG, "The random set is ${mutableListOf(randomPiece[0])}")
        return randomPiece
    }

    fun piecePosition(square: Square): ChessPiece? {
        return piecePosition(square.col, square.row)
    }

    private fun piecePosition(col: Int, row: Int): ChessPiece? {
        for (piece in pieceSet) {
            if (col == piece.col && row == piece.row) {
                return piece
            }
        }
        return null
    }

    override fun toString(): String {
        var board = " "

        for (row in 0..4) {
            board += "$row"
            for (col in 0..8) {
                val piece = piecePosition(col, row)
                if (piece == null) {
                    board += " ."
                } else {
                    val black = piece.player == Player.BLACK
                    board += " " + when(piece.cType) {
                        ChessType.KING1 -> "K"
                        ChessType.KING2 -> "k"
                        ChessType.PAWN1 -> "P"
                        ChessType.PAWN2 -> "p"
                        ChessType.GUARD -> if (black) "G" else "g"
                        ChessType.ADVISER -> if (black) "A" else "a"
                        ChessType.HORSE -> if (black) "H" else "h"
                        ChessType.ROOK -> if (black) "R" else "r"
                        ChessType.CANNON -> if (black) "C" else "c"
                    }
                }
            }
            board += " \n"
        }
        board += "  0 1 2 3 4 5 6 7 8"

        return board

    }
}

