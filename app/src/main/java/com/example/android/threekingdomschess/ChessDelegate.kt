package com.example.android.threekingdomschess

//Interface defines a function without its body
interface ChessDelegate {
    fun piecePosition(square: Square): ChessPiece?
    fun movePiece (from: Square, to: Square)
}