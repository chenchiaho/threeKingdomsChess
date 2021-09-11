package com.example.android.threekingdomschess.model

import com.example.android.threekingdomschess.pieces.ChessType

data class ChessPiece(
        val col: Int,
        val row: Int,
        val player: Player,
        val cType: ChessType,
        var resId: Int
    )