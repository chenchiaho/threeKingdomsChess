package com.example.android.threekingdomschess

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.android.threekingdomschess.model.Player
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.pieces.ChessPiece
import com.example.android.threekingdomschess.pieces.ChessType
import com.example.android.threekingdomschess.pieces.PieceLogic
import kotlin.math.log


object ChessGame {

    private val pieceLogic = PieceLogic()
    private var pieceSet = mutableSetOf<ChessPiece>()
    private var greenScore = 12
    private var blackScore = 10
    private var redScore = 10

    private var currentPlayer = Player.GREEN

//    var king =

    init {
        initPosition()
        reset()
        }

    private fun clear() {
        pieceSet.clear()
    }

    private fun addPiece(piece: ChessPiece) {
        pieceSet.add(piece)
    }

    private fun legalMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row){
            return false
        } else if (to.col < 0 || to.col > 8 || to.row < 0 || to.row > 4)
            return false
        val assignLegalMove = piecePositionSquare(from) ?: return false
        return when(assignLegalMove.cType) {
            ChessType.HORSE -> pieceLogic.horseLegal(from, to)
            ChessType.ROOK -> pieceLogic.rookLegal(from, to)
            ChessType.KING -> pieceLogic.rookLegal(from, to)
            ChessType.ADVISER -> pieceLogic.adviserLegal(from, to)
            ChessType.PAWN -> pieceLogic.pawnLegal(from, to)
            ChessType.GUARD -> pieceLogic.guardLegal(from, to)
            ChessType.CANNON -> pieceLogic.cannonLegal(from, to)
        }
    }

    fun movePiece(from: Square, to: Square) {
        if (legalMove(from, to) && piecePositionSquare(from)?.player == currentPlayer) {
        movePieceLogic(from.col, from.row, to.col, to.row)
        onGameEnd()
        }
    }

    private fun movePieceLogic(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {

        if (fromCol == toCol && fromRow == toRow) return
        val movingPiece = checkPiecePosition(fromCol, fromRow) ?: return

        checkPiecePosition(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                return
            } else {
                when (it.player) {
                    Player.GREEN -> greenScore - 1
                    Player.BLACK -> blackScore - 1
                    Player.RED -> redScore - 1
                }
                pieceSet.remove(it)
            }
        }
        pieceSet.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow))
        currentPlayer = nextPlayer(movingPiece)
        nextColor(movingPiece)
        Log.d("currentPlayer", "$currentPlayer green: $greenScore, black: $blackScore, red: $redScore")


//        val animate = TranslateAnimation(R.drawable.chess_k1.getX, toCol-fromCol.toFloat(), movingPiece.row.toFloat(), toRow-fromRow.toFloat())
//        animate.duration = 1000
//        animate.start()
    }

    private fun nextPlayer(piece: ChessPiece): Player {

        return when (piece.player) {
            Player.GREEN -> if (blackScore == 0) {Player.RED} else Player.BLACK
            Player.BLACK -> if (redScore == 0) {Player.GREEN} else Player.RED
            Player.RED -> if (greenScore == 0) {Player.BLACK} else Player.GREEN
        }
    }

    fun nextColor(piece: ChessPiece) {
        when (piece.player) {

            Player.RED -> MainFragment().xiang?.setColorFilter(R.color.red)
            Player.BLACK -> MainFragment().xiang?.setColorFilter(R.color.black)
            Player.GREEN -> MainFragment().xiang?.colorFilter = null
        }
    }

    fun reset() {
        val position = initPosition()
        clear()

        for (i in 0..31) {
            when(i) {
                0 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING, R.drawable.g_general_w))
                1 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING, R.drawable.g_general_w))
                in 2..6 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN, R.drawable.g_pawn_w))
                in 7..11 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN, R.drawable.g_pawn_w))

                12, 13 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.GUARD, R.drawable.b_guard_w))
                14, 15 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.ADVISER, R.drawable.b_elephant_w))
                16, 17 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.HORSE, R.drawable.b_horse_w))
                18, 19 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.ROOK, R.drawable.b_rook_w))
                20, 21 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.CANNON, R.drawable.b_cannon_w))

                22, 23 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.GUARD, R.drawable.r_guard_w))
                24, 25 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ADVISER, R.drawable.r_elephant_w))
                26, 27 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.HORSE, R.drawable.r_horse_w))
                28, 29 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ROOK, R.drawable.r_rook_w))
                30, 31 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.CANNON, R.drawable.r_cannon_w))

            }
        }
        greenScore = 12
        blackScore = 10
        redScore = 10
        currentPlayer = Player.GREEN
    }

    private fun initPosition(): MutableList<Square> {

        val randomPiece = mutableListOf<Square>()

        (0..4).filter { it !=2 }.forEach { row ->
            (0..8).filter { it != 4 }.forEach { col ->

                randomPiece.add(Square(col, row))
            }
        }

        randomPiece.shuffle()
        return randomPiece
    }

    fun piecePositionSquare(square: Square): ChessPiece? {
        return checkPiecePosition(square.col, square.row)
    }

    fun checkPiecePosition(col: Int, row: Int): ChessPiece? {
        for (piece in pieceSet) {
            if (col == piece.col && row == piece.row) {
                return piece
            }
        }
        return null
    }

    fun onGameEnd() {
        if (greenScore == 0 && blackScore == 0) {
            MainFragment().gameEndDialog("RED")
        } else if (blackScore == 0 && redScore == 0) {
            MainFragment().gameEndDialog("GREEN")
        } else if (redScore == 0 && greenScore == 0) {
            MainFragment().gameEndDialog("BLACK")
        }
    }


}

