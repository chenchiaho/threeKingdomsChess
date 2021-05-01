package com.example.android.threekingdomschess

import kotlin.math.abs

object ChessGame {

    private var pieceSet = mutableSetOf<ChessPiece>()

    init {
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

    private fun canonLegal(from: Square, to: Square): Boolean {
        if (from.col == to.col && rookVerticalPath(from, to) ||
                from.row == to.row && rookHorizontalPath(from, to)) {
            return true
        }
        return false
    }

    private fun legalMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row){
            return false
        }
        val legalMove = piecePosition(from) ?: return false
        when(legalMove.cType) {
            ChessType.HORSE -> return horseLegal(from, to)
            ChessType.ROOK -> return rookLegal(from, to)
            ChessType.KING1 -> return rookLegal(from, to)
            ChessType.KING2 -> return rookLegal(from, to)
            ChessType.ADVISER -> return adviserLegal(from, to)
            ChessType.PAWN1 -> return pawnLegal(from, to)
            ChessType.PAWN2 -> return pawnLegal(from, to)
            ChessType.GUARD -> return guardLegal(from, to)
            ChessType.CANNON -> return canonLegal(from, to)
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
        clear()
        addPiece(ChessPiece(0, 4, Player.GREEN, ChessType.KING1, R.drawable.chess_k1))
        addPiece(ChessPiece(1, 4, Player.GREEN, ChessType.KING2, R.drawable.chess_k2))
        addPiece(ChessPiece(2, 4, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(3, 4, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(5, 4, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(6, 4, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(7, 4, Player.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        addPiece(ChessPiece(8, 4, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(0, 3, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(1, 3, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(2, 3, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        addPiece(ChessPiece(3, 3, Player.GREEN, ChessType.PAWN2, R.drawable.chess_p2))

        addPiece(ChessPiece(5, 3, Player.BLACK, ChessType.GUARD, R.drawable.chess_g1))
        addPiece(ChessPiece(6, 3, Player.BLACK, ChessType.GUARD, R.drawable.chess_g1))
        addPiece(ChessPiece(7, 3, Player.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
        addPiece(ChessPiece(8, 3, Player.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
        addPiece(ChessPiece(0, 1, Player.BLACK, ChessType.HORSE, R.drawable.chess_h1))
        addPiece(ChessPiece(1, 1, Player.BLACK, ChessType.HORSE, R.drawable.chess_h1))
        addPiece(ChessPiece(2, 1, Player.BLACK, ChessType.ROOK, R.drawable.chess_r1))
        addPiece(ChessPiece(3, 1, Player.BLACK, ChessType.ROOK, R.drawable.chess_r1))
        addPiece(ChessPiece(5, 1, Player.BLACK, ChessType.CANNON, R.drawable.chess_c1))
        addPiece(ChessPiece(6, 1, Player.BLACK, ChessType.CANNON, R.drawable.chess_c1))

        addPiece(ChessPiece(7, 1, Player.RED, ChessType.GUARD, R.drawable.chess_g2))
        addPiece(ChessPiece(8, 1, Player.RED, ChessType.GUARD, R.drawable.chess_g2))
        addPiece(ChessPiece(0, 0, Player.RED, ChessType.ADVISER, R.drawable.chess_b2))
        addPiece(ChessPiece(1, 0, Player.RED, ChessType.ADVISER, R.drawable.chess_b2))
        addPiece(ChessPiece(2, 0, Player.RED, ChessType.HORSE, R.drawable.chess_h2))
        addPiece(ChessPiece(3, 0, Player.RED, ChessType.HORSE, R.drawable.chess_h2))
        addPiece(ChessPiece(5, 0, Player.RED, ChessType.ROOK, R.drawable.chess_r2))
        addPiece(ChessPiece(6, 0, Player.RED, ChessType.ROOK, R.drawable.chess_r2))
        addPiece(ChessPiece(7, 0, Player.RED, ChessType.CANNON, R.drawable.chess_c2))
        addPiece(ChessPiece(8, 0, Player.RED, ChessType.CANNON, R.drawable.chess_c2))
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

