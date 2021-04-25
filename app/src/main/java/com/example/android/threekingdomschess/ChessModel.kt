package com.example.android.threekingdomschess

class ChessModel() {

    var pieceSet = mutableSetOf<ChessPiece>()

    init {
        reset()
        }

    fun movePiece (fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        //?: = if operand1 is null, run operand2
        val movingPiece = piecePosition(fromCol, fromRow) ?: return

        piecePosition(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                return
            }
            pieceSet.remove(it)
        }
        pieceSet.remove(movingPiece)
        pieceSet.add(ChessPiece(toCol, toRow, movingPiece.player, movingPiece.cType, movingPiece.resId))
    }

    fun reset() {
        pieceSet.add(ChessPiece(0,4, ChessPlayer.GREEN, ChessType.KING1, R.drawable.chess_k1))
        pieceSet.add(ChessPiece(1,4, ChessPlayer.GREEN, ChessType.KING2, R.drawable.chess_k2))
        pieceSet.add(ChessPiece(2, 4, ChessPlayer.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        pieceSet.add(ChessPiece(3, 4, ChessPlayer.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        pieceSet.add(ChessPiece(5, 4, ChessPlayer.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        pieceSet.add(ChessPiece(6, 4, ChessPlayer.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        pieceSet.add(ChessPiece(7, 4, ChessPlayer.GREEN, ChessType.PAWN1, R.drawable.chess_p1))
        pieceSet.add(ChessPiece(8, 4, ChessPlayer.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        pieceSet.add(ChessPiece(0, 3, ChessPlayer.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        pieceSet.add(ChessPiece(1, 3, ChessPlayer.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        pieceSet.add(ChessPiece(2, 3, ChessPlayer.GREEN, ChessType.PAWN2, R.drawable.chess_p2))
        pieceSet.add(ChessPiece(3, 3, ChessPlayer.GREEN, ChessType.PAWN2, R.drawable.chess_p2))

        pieceSet.add(ChessPiece(5, 3, ChessPlayer.BLACK, ChessType.GUARD, R.drawable.chess_g1))
        pieceSet.add(ChessPiece(6, 3, ChessPlayer.BLACK, ChessType.GUARD, R.drawable.chess_g1))
        pieceSet.add(ChessPiece(7, 3, ChessPlayer.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
        pieceSet.add(ChessPiece(8, 3, ChessPlayer.BLACK, ChessType.ADVISER, R.drawable.chess_b1))
        pieceSet.add(ChessPiece(0, 1, ChessPlayer.BLACK, ChessType.HORSE, R.drawable.chess_h1))
        pieceSet.add(ChessPiece(1, 1, ChessPlayer.BLACK, ChessType.HORSE, R.drawable.chess_h1))
        pieceSet.add(ChessPiece(2, 1, ChessPlayer.BLACK, ChessType.ROOK, R.drawable.chess_r1))
        pieceSet.add(ChessPiece(3, 1, ChessPlayer.BLACK, ChessType.ROOK, R.drawable.chess_r1))
        pieceSet.add(ChessPiece(5, 1, ChessPlayer.BLACK, ChessType.CANNON, R.drawable.chess_c1))
        pieceSet.add(ChessPiece(6, 1, ChessPlayer.BLACK, ChessType.CANNON, R.drawable.chess_c1))

        pieceSet.add(ChessPiece(7, 1, ChessPlayer.RED, ChessType.GUARD, R.drawable.chess_g2))
        pieceSet.add(ChessPiece(8, 1, ChessPlayer.RED, ChessType.GUARD, R.drawable.chess_g2))
        pieceSet.add(ChessPiece(0, 0, ChessPlayer.RED, ChessType.ADVISER, R.drawable.chess_b2))
        pieceSet.add(ChessPiece(1, 0, ChessPlayer.RED, ChessType.ADVISER, R.drawable.chess_b2))
        pieceSet.add(ChessPiece(2, 0, ChessPlayer.RED, ChessType.HORSE, R.drawable.chess_h2))
        pieceSet.add(ChessPiece(3, 0, ChessPlayer.RED, ChessType.HORSE, R.drawable.chess_h2))
        pieceSet.add(ChessPiece(5, 0, ChessPlayer.RED, ChessType.ROOK, R.drawable.chess_r2))
        pieceSet.add(ChessPiece(6, 0, ChessPlayer.RED, ChessType.ROOK, R.drawable.chess_r2))
        pieceSet.add(ChessPiece(7, 0, ChessPlayer.RED, ChessType.CANNON, R.drawable.chess_c2))
        pieceSet.add(ChessPiece(8, 0, ChessPlayer.RED, ChessType.CANNON, R.drawable.chess_c2))
    }

    fun piecePosition(col: Int, row: Int): ChessPiece? {
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
                var piece = piecePosition(col, row)
                if (piece == null) {
                    board += " ."
                } else {
                    val black = piece.player == ChessPlayer.BLACK
                    board += " " + when(piece.cType) {
                        ChessType.KING1 -> "K"
                        ChessType.KING2 -> "k"
                        ChessType.PAWN1 -> "P"
                        ChessType.PAWN2 -> "p"
                        ChessType.GUARD -> {
                            if (black) "G" else "g"
                        }
                        ChessType.ADVISER -> {
                            if (black) "A" else "a"
                        }
                        ChessType.HORSE -> {
                            if (black) "H" else "h"
                        }
                        ChessType.ROOK -> {
                            if (black) "R" else "r"
                        }
                        ChessType.CANNON -> {
                            if (black) "C" else "c"
                        }
                    }
                }
            }
            board += " \n"
        }
        board += "  0 1 2 3 4 5 6 7 8"

        return board

    }
}

