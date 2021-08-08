package com.example.android.threekingdomschess.pieces

import com.example.android.threekingdomschess.model.Player

data class ChessPiece(
    val col: Int,
    val row: Int,
    val player: Player,
    val cType: ChessType,
    val resId: Int)