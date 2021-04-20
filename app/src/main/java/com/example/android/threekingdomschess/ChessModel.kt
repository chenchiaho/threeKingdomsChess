package com.example.android.threekingdomschess

class ChessModel() {

    var pieceSet = mutableSetOf<ChessPiece>()

    init {
        reset()
    }

    private fun reset() {

        pieceSet.add(ChessPiece(0,4, ChessPlayer.GREEN, ChessType.KING1))
        pieceSet.add(ChessPiece(1,4, ChessPlayer.GREEN, ChessType.KING2))
        pieceSet.add(ChessPiece(2, 4, ChessPlayer.GREEN, ChessType.PAWN1))
        pieceSet.add(ChessPiece(3, 4, ChessPlayer.GREEN, ChessType.PAWN1))
        pieceSet.add(ChessPiece(5, 4, ChessPlayer.GREEN, ChessType.PAWN1))
        pieceSet.add(ChessPiece(6, 4, ChessPlayer.GREEN, ChessType.PAWN1))
        pieceSet.add(ChessPiece(7, 4, ChessPlayer.GREEN, ChessType.PAWN1))
        pieceSet.add(ChessPiece(8, 4, ChessPlayer.GREEN, ChessType.PAWN2))
        pieceSet.add(ChessPiece(0, 3, ChessPlayer.GREEN, ChessType.PAWN2))
        pieceSet.add(ChessPiece(1, 3, ChessPlayer.GREEN, ChessType.PAWN2))
        pieceSet.add(ChessPiece(2, 3, ChessPlayer.GREEN, ChessType.PAWN2))
        pieceSet.add(ChessPiece(3, 3, ChessPlayer.GREEN, ChessType.PAWN2))

        pieceSet.add(ChessPiece(5, 3, ChessPlayer.BLACK, ChessType.GUARD))
        pieceSet.add(ChessPiece(6, 3, ChessPlayer.BLACK, ChessType.GUARD))
        pieceSet.add(ChessPiece(7, 3, ChessPlayer.BLACK, ChessType.ADVISER))
        pieceSet.add(ChessPiece(8, 3, ChessPlayer.BLACK, ChessType.ADVISER))
        pieceSet.add(ChessPiece(0, 1, ChessPlayer.BLACK, ChessType.HORSE))
        pieceSet.add(ChessPiece(1, 1, ChessPlayer.BLACK, ChessType.HORSE))
        pieceSet.add(ChessPiece(2, 1, ChessPlayer.BLACK, ChessType.ROOK))
        pieceSet.add(ChessPiece(3, 1, ChessPlayer.BLACK, ChessType.ROOK))
        pieceSet.add(ChessPiece(5, 1, ChessPlayer.BLACK, ChessType.CANNON))
        pieceSet.add(ChessPiece(6, 1, ChessPlayer.BLACK, ChessType.CANNON))

        pieceSet.add(ChessPiece(7, 1, ChessPlayer.RED, ChessType.GUARD))
        pieceSet.add(ChessPiece(8, 1, ChessPlayer.RED, ChessType.GUARD))
        pieceSet.add(ChessPiece(0, 0, ChessPlayer.RED, ChessType.ADVISER))
        pieceSet.add(ChessPiece(1, 0, ChessPlayer.RED, ChessType.ADVISER))
        pieceSet.add(ChessPiece(2, 0, ChessPlayer.RED, ChessType.HORSE))
        pieceSet.add(ChessPiece(3, 0, ChessPlayer.RED, ChessType.HORSE))
        pieceSet.add(ChessPiece(5, 0, ChessPlayer.RED, ChessType.ROOK))
        pieceSet.add(ChessPiece(6, 0, ChessPlayer.RED, ChessType.ROOK))
        pieceSet.add(ChessPiece(7, 0, ChessPlayer.RED, ChessType.CANNON))
        pieceSet.add(ChessPiece(8, 0, ChessPlayer.RED, ChessType.CANNON))


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

        for (row in 4 downTo 0) {
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

