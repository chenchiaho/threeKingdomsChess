package com.example.android.threekingdomschess.pieces

import com.example.android.threekingdomschess.ChessGame
import com.example.android.threekingdomschess.model.Square
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class PieceLogic {

    fun horseLegal(from: Square, to: Square): Boolean {
        return abs(from.col - to.col) == 2 && abs(from.row - to.row) == 1 ||
                abs(from.col - to.col) == 1 && abs(from.row - to.row) == 2
    }

    fun rookLegal(from: Square, to: Square): Boolean {
        if (from.col == to.col && rookVerticalPath(from, to) ||
                from.row == to.row && rookHorizontalPath(from, to)) {
            return true
        }
        return false
    }

    private fun rookVerticalPath(from: Square, to: Square): Boolean {
        if (from.col != to.col) return false
        val gap = abs(from.row - to.row) -1
        if (gap == 0) return true
        for (i in 1..gap) {
            val nextRow = if (to.row > from.row) from.row + i else from.row -i
            if (ChessGame.piecePositionSquare(Square(from.col, nextRow)) !=null) {
                return false
            }
        }
        return true
    }

    private fun rookHorizontalPath(from: Square, to: Square): Boolean {
        if (from.row != to.row) return false
        val gap = abs(from.col - to.col) -1
        if (gap == 0) return true
        for (i in 1..gap) {
            val nextCol = if (to.col > from.col) from.col + i else from.col -i
            if (ChessGame.piecePositionSquare(Square(nextCol, from.row)) !=null) {
                return false
            }
        }
        return true
    }

    fun adviserLegal(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == 2 &&
                abs(to.row - from.row) == 2) {
            return true
        }
        return false
    }

    fun guardLegal(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == 1 &&
                abs(to.row - from.row) == 1) {
            return true
        }
        return false
    }

    fun pawnLegal(from: Square, to: Square): Boolean {
        if (abs(from.col - to.col) == 1 &&
                abs(to.row - from.row) == 0) {
            return true
        } else if (abs(from.col - to.col) == 0 &&
                abs(to.row - from.row) == 1) {
            return true
        }
        return false
    }

    fun cannonLegal(from: Square, to: Square): Boolean {
        if (ChessGame.checkPiecePosition(to.col, to.row) == null &&
                rookLegal(from, to)) {
            return true

        } else if(ChessGame.checkPiecePosition(to.col, to.row) != null &&
                (from.col == to.col || from.row == to.row) &&
                piecesBetween(from.col, from.row, to.col, to.row)) {
            return true
        }
            return false
    }

    private fun piecesBetween(fromCol: Int, fromRow: Int,
                              toCol: Int, toRow: Int): Boolean {
        var count = 0
        val start: Int
        val end: Int

        if (fromCol == toCol) {
            start = min(fromRow, toRow)
            end = max(fromRow, toRow)
            for (row in start + 1 until end) {
                count += if (ChessGame.checkPiecePosition(fromCol, row) == null) 0 else 1
            }
        } else {
            start = min(fromCol, toCol)
            end = max(fromCol, toCol)
            for (col in start + 1 until end) {
                count += if (ChessGame.checkPiecePosition(col, fromRow) == null) 0 else 1
            }
        }
        return count == 1
    }

}