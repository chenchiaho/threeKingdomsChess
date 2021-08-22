package com.example.android.threekingdomschess

import android.util.Log
import com.example.android.threekingdomschess.model.Player
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.pieces.ChessPiece
import com.example.android.threekingdomschess.pieces.ChessType
import com.example.android.threekingdomschess.pieces.PieceLogic


object ChessGame {

    private val pieceLogic = PieceLogic()
    private var pieceSet = mutableSetOf<ChessPiece>()
    private var greenScore = 12
    private var blackScore = 10
    private var redScore = 10
    var isWestern = true

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
            ChessType.KING1 -> pieceLogic.rookLegal(from, to)
            ChessType.KING2 -> pieceLogic.rookLegal(from, to)
            ChessType.ADVISER -> pieceLogic.adviserLegal(from, to)
            ChessType.PAWN1 -> pieceLogic.pawnLegal(from, to)
            ChessType.PAWN2 -> pieceLogic.pawnLegal(from, to)
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

            Player.RED -> MainFragment().playerIndicator?.setColorFilter(R.color.red)
            Player.BLACK -> MainFragment().playerIndicator?.setColorFilter(R.color.black)
            Player.GREEN -> MainFragment().playerIndicator?.colorFilter = null
        }
    }

    fun reset() {
        val position = initPosition()
        clear()

        for (i in 0..31) {

            when(i) {
                0 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING1,
                        if (isWestern) {
                            R.drawable.g_general_w
                        } else R.drawable.g_general_c1
                ))
                1 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING2,
                        if (isWestern) {
                            R.drawable.g_general_w
                        } else R.drawable.g_general_c2
                ))
                in 2..6 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN1,
                        if (isWestern) {
                            R.drawable.g_pawn_w
                        } else R.drawable.g_pawn_c1
                ))
                in 7..11 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN2,
                        if (isWestern) {
                            R.drawable.g_pawn_w
                        } else R.drawable.g_pawn_c2
                        ))

                12, 13 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.GUARD,
                        if (isWestern) {
                            R.drawable.b_guard_w
                        } else R.drawable.b_guard_c
                ))
                14, 15 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.ADVISER,
                        if (isWestern) {
                            R.drawable.b_elephant_w
                        } else R.drawable.b_elephant_c
                ))
                16, 17 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.HORSE,
                        if (isWestern) {
                            R.drawable.b_horse_w
                        } else R.drawable.b_horse_c
                ))
                18, 19 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.ROOK,
                        if (isWestern) {
                            R.drawable.b_rook_w
                        } else R.drawable.b_rook_c
                ))
                20, 21 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.BLACK, ChessType.CANNON,
                        if (isWestern) {
                            R.drawable.b_cannon_w
                        } else R.drawable.b_cannon_c
                ))

                22, 23 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.GUARD,
                        if (isWestern) {
                            R.drawable.r_guard_w
                        } else R.drawable.r_guard_c
                ))
                24, 25 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ADVISER,
                        if (isWestern) {
                            R.drawable.r_elephant_w
                        } else R.drawable.r_elephant_c
                ))
                26, 27 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.HORSE,
                        if (isWestern) {
                            R.drawable.r_horse_w
                        } else R.drawable.r_horse_w
                ))
                28, 29 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ROOK,
                        if (isWestern) {
                            R.drawable.r_rook_w
                        } else R.drawable.r_rook_c
                ))
                30, 31 -> addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.CANNON,
                        if (isWestern) {
                            R.drawable.r_cannon_w
                        } else R.drawable.r_cannon_c
                ))

            }
        }
        greenScore = 12
        blackScore = 10
        redScore = 10
        currentPlayer = Player.GREEN
    }

    fun switchStyle() {

        if (isWestern) {

            Log.d("isWestern", "$isWestern")
            for (piece in pieceSet) {

                when (piece.cType) {
                    ChessType.KING1 -> piece.resId = R.drawable.g_general_c1
                    ChessType.KING2 -> piece.resId = R.drawable.g_general_c2
                    ChessType.PAWN1 -> piece.resId = R.drawable.g_pawn_c1
                    ChessType.PAWN2 -> piece.resId = R.drawable.g_pawn_c2
                    ChessType.GUARD -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_guard_c
                        else -> piece.resId = R.drawable.r_guard_c
                    }
                    ChessType.ADVISER -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_elephant_c
                        else -> piece.resId = R.drawable.r_elephant_c
                    }
                    ChessType.HORSE -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_horse_c
                        else -> piece.resId = R.drawable.r_horse_c
                    }
                    ChessType.ROOK -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_rook_c
                        else -> piece.resId = R.drawable.r_rook_c
                    }
                    ChessType.CANNON -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_cannon_c
                        else -> piece.resId = R.drawable.r_cannon_c
                    }
                }
            }
        } else  {

            Log.d("isWestern", "$isWestern")
            for (piece in pieceSet) {
                when (piece.cType) {
                    ChessType.KING1 -> piece.resId = R.drawable.g_general_w
                    ChessType.KING2 -> piece.resId = R.drawable.g_general_w
                    ChessType.PAWN1 -> piece.resId = R.drawable.g_pawn_w
                    ChessType.PAWN2 -> piece.resId = R.drawable.g_pawn_w
                    ChessType.GUARD -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_guard_w
                        else -> piece.resId = R.drawable.r_guard_w
                    }
                    ChessType.ADVISER -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_elephant_w
                        else -> piece.resId = R.drawable.r_elephant_w
                    }
                    ChessType.HORSE -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_horse_w
                        else -> piece.resId = R.drawable.r_horse_w
                    }
                    ChessType.ROOK -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_rook_w
                        else -> piece.resId = R.drawable.r_rook_w
                    }
                    ChessType.CANNON -> when (piece.player) {
                        Player.BLACK -> piece.resId = R.drawable.b_cannon_w
                        else -> piece.resId = R.drawable.r_cannon_w
                    }
                }
            }
        }
        isWestern = !isWestern
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

