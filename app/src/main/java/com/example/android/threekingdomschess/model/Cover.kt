package com.example.android.threekingdomschess.model

import com.example.android.threekingdomschess.pieces.ChessType

data class Cover(
        val row: Int,
        val col: Int,
        var resId: Int
)