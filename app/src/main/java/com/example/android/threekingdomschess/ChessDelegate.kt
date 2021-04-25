package com.example.android.threekingdomschess

//Interface defines a function without its body
interface ChessDelegate {
    fun piecePosition(col: Int, row: Int): ChessPiece?
    fun movePiece (fromCol: Int, fromRow: Int, toCol: Int, toRow: Int)
}