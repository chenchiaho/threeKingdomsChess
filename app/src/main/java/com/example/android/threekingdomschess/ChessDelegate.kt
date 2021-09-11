package com.example.android.threekingdomschess

import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.model.ChessPiece

//Interface defines a function without its body
interface ChessDelegate {
    fun assignPiecePosition(square: Square): ChessPiece?
    fun movePiece (from: Square, to: Square)
}