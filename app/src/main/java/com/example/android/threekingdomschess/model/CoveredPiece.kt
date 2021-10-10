package com.example.android.threekingdomschess.model

import com.example.android.threekingdomschess.pieces.ChessType

data class CoveredPiece(
        val row: Int,
        val col: Int,
        val cType: ChessType,
        var resId: Int
)