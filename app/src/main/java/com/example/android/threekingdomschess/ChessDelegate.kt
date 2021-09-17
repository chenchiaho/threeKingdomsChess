package com.example.android.threekingdomschess

import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.model.ChessPiece
import com.example.android.threekingdomschess.model.Cover

//Interface defines a function without its body
interface ChessDelegate {
    fun assignCoverPosition(square: Square): Cover?
    fun removeCover(fromCol: Int, fromRow: Int)
    fun assignPiecePosition(square: Square): ChessPiece?
    fun movePiece (from: Square, to: Square)
}