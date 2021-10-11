package com.example.android.threekingdomschess

import android.provider.Settings.Global.getString
import com.example.android.threekingdomschess.model.Player
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.model.ChessPiece
import com.example.android.threekingdomschess.model.CoveredPiece
import com.example.android.threekingdomschess.pieces.ChessType
import com.example.android.threekingdomschess.pieces.PieceLogic


object ChessGame {

    private val pieceLogic = PieceLogic()
    private var pieceSet = mutableSetOf<ChessPiece>()
    var coverSet = mutableSetOf<CoveredPiece>()
    private var greenScore = 12
    private var purpleScore = 10
    private var redScore = 10
    var isWestern = true

    private var currentPlayer = Player.GREEN

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

    private fun addCover(coveredPiece: CoveredPiece) {
        coverSet.add(coveredPiece)
    }

    private fun legalMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row) {
            return false
        } else if (to.col < 0 || to.col > 8 || to.row < 0 || to.row > 4)
            return false
        val assignLegalMove = piecePositionSquare(from) ?: return false
        return when (assignLegalMove.cType) {
            ChessType.HORSE -> pieceLogic.horseLegal(from, to)
            ChessType.ROOK -> pieceLogic.rookLegal(from, to)
            ChessType.KING1 -> pieceLogic.rookLegal(from, to)
            ChessType.KING2 -> pieceLogic.rookLegal(from, to)
            ChessType.ADVISER -> pieceLogic.adviserLegal(from, to)
            ChessType.PAWN1 -> pieceLogic.pawnLegal(from, to)
            ChessType.PAWN2 -> pieceLogic.pawnLegal(from, to)
            ChessType.GUARD -> pieceLogic.guardLegal(from, to)
            ChessType.CANNON -> pieceLogic.cannonLegal(from, to)
            ChessType.COVER -> false
        }
    }

    fun turnPiece(fromCol: Int, fromRow: Int) {
        val selectedPiece = checkCoverPosition(fromCol, fromRow)
        if (selectedPiece?.resId == R.drawable.cover) {
            coverSet.remove(selectedPiece)
            currentPlayer = nextPlayerByPlayer(currentPlayer)
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
            if (it.player == movingPiece.player || checkCoverPosition(toCol, toRow)?.resId != null) {
                return
            } else if (checkCoverPosition(toCol, toRow)?.resId == null) {
                when (it.player) {

                    Player.GREEN -> greenScore -= 1
                    Player.PURPLE -> purpleScore -= 1
                    Player.RED -> redScore -= 1
                }
                pieceSet.remove(it)
            }
        }

        pieceSet.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow))
        currentPlayer = nextPlayer(movingPiece)

    }

    private fun nextPlayer(piece: ChessPiece): Player {
        return nextPlayerByPlayer(piece.player)
    }

    private fun nextPlayerByPlayer(player: Player): Player {
        return when (player) {
            Player.GREEN -> if (purpleScore == 0) {Player.RED} else Player.PURPLE
            Player.PURPLE -> if (redScore == 0) {Player.GREEN} else Player.RED
            Player.RED -> if (greenScore == 0) {Player.PURPLE} else Player.GREEN
        }
    }

    fun nextTurn(): Player {
        return currentPlayer
    }

    fun reset() {
        val position = initPosition()
        clear()

        for (i in 0..31) {
            when (i) {
                0 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING1,
                            if (isWestern) {
                                R.drawable.g_general_w
                            } else R.drawable.g_general_c1))
                }
                1 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.KING2,
                            if (isWestern) {
                                R.drawable.g_queen_w
                            } else R.drawable.g_general_c2))
                }
                in 2..6 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN1,
                            if (isWestern) {
                                R.drawable.g_pawn_w
                            } else R.drawable.g_pawn_c1))
                }
                in 7..11 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.GREEN, ChessType.PAWN2,
                            if (isWestern) {
                                R.drawable.g_pawn_w
                            } else R.drawable.g_pawn_c2))
                }

                12, 13 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.PURPLE, ChessType.GUARD,
                            if (isWestern) {
                                R.drawable.b_guard_w
                            } else R.drawable.b_guard_c))
                }
                14, 15 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.PURPLE, ChessType.ADVISER,
                            if (isWestern) {
                                R.drawable.b_elephant_w
                            } else R.drawable.b_elephant_c))
                }
                16, 17 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.PURPLE, ChessType.HORSE,
                            if (isWestern) {
                                R.drawable.b_horse_w
                            } else R.drawable.b_horse_c))
                }
                18, 19 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.PURPLE, ChessType.ROOK,
                            if (isWestern) {
                                R.drawable.b_rook_w
                            } else R.drawable.b_rook_c))
                }
                20, 21 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.PURPLE, ChessType.CANNON,
                            if (isWestern) {
                                R.drawable.b_cannon_w
                            } else R.drawable.b_cannon_c))
                }

                22, 23 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.GUARD,
                            if (isWestern) {
                                R.drawable.r_guard_w
                            } else R.drawable.r_guard_c))
                }
                24, 25 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ADVISER,
                            if (isWestern) {
                                R.drawable.r_elephant_w
                            } else R.drawable.r_elephant_c))
                }
                26, 27 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.HORSE,
                            if (isWestern) {
                                R.drawable.r_horse_w
                            } else R.drawable.r_horse_c))
                }
                28, 29 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.ROOK,
                            if (isWestern) {
                                R.drawable.r_rook_w
                            } else R.drawable.r_rook_c))
                }
                30, 31 -> {
                    addPiece(ChessPiece(position[i].col, position[i].row, Player.RED, ChessType.CANNON,
                            if (isWestern) {
                                R.drawable.r_cannon_w
                            } else R.drawable.r_cannon_c))
                }
            }
        }

        (0..4).filter { it !=2 }.forEach { row ->
            (0..8).filter { it != 4 }.forEach { col ->
                addCover(CoveredPiece(row, col, ChessType.COVER, R.drawable.cover))
            }
        }

        greenScore = 12
        purpleScore = 10
        redScore = 10
        currentPlayer = Player.GREEN


    }

    fun switchStyle() {
        val newList = mutableListOf<ChessPiece>()
        if (isWestern) {
            for (piece in pieceSet) {
                when (piece.resId) {

                    R.drawable.g_general_w -> piece.resId = R.drawable.g_general_c1
                    R.drawable.g_queen_w -> piece.resId = R.drawable.g_general_c2

                    R.drawable.g_pawn_w ->
                        if (piece.cType == ChessType.PAWN1) {
                            piece.resId = R.drawable.g_pawn_c1
                        } else piece.resId = R.drawable.g_pawn_c2

                    R.drawable.b_guard_w -> piece.resId = R.drawable.b_guard_c
                    R.drawable.r_guard_w -> piece.resId = R.drawable.r_guard_c
                    R.drawable.b_elephant_w -> piece.resId = R.drawable.b_elephant_c
                    R.drawable.r_elephant_w -> piece.resId = R.drawable.r_elephant_c
                    R.drawable.b_horse_w -> piece.resId = R.drawable.b_horse_c
                    R.drawable.r_horse_w -> piece.resId = R.drawable.r_horse_c
                    R.drawable.b_rook_w -> piece.resId = R.drawable.b_rook_c
                    R.drawable.r_rook_w -> piece.resId = R.drawable.r_rook_c
                    R.drawable.b_cannon_w -> piece.resId = R.drawable.b_cannon_c
                    R.drawable.r_cannon_w -> piece.resId = R.drawable.r_cannon_c
                }
                newList.add(piece)
            }
        } else {
            for (piece in pieceSet) {
                when (piece.resId) {
                    R.drawable.g_general_c1 -> piece.resId = R.drawable.g_general_w
                    R.drawable.g_general_c2 -> piece.resId = R.drawable.g_queen_w
                    R.drawable.g_pawn_c1 -> piece.resId = R.drawable.g_pawn_w
                    R.drawable.g_pawn_c2 -> piece.resId = R.drawable.g_pawn_w
                    R.drawable.b_guard_c -> piece.resId = R.drawable.b_guard_w
                    R.drawable.r_guard_c -> piece.resId = R.drawable.r_guard_w
                    R.drawable.b_elephant_c -> piece.resId = R.drawable.b_elephant_w
                    R.drawable.r_elephant_c -> piece.resId = R.drawable.r_elephant_w
                    R.drawable.b_horse_c -> piece.resId = R.drawable.b_horse_w
                    R.drawable.r_horse_c -> piece.resId = R.drawable.r_horse_w
                    R.drawable.b_rook_c -> piece.resId = R.drawable.b_rook_w
                    R.drawable.r_rook_c -> piece.resId = R.drawable.r_rook_w
                    R.drawable.b_cannon_c -> piece.resId = R.drawable.b_cannon_w
                    R.drawable.r_cannon_c -> piece.resId = R.drawable.r_cannon_w
                }
                newList.add(piece)
            }
        }
        pieceSet.clear()
        pieceSet.addAll(newList)
        isWestern = !isWestern
    }

    private fun initPosition(): MutableList<Square> {

        val randomPiece = mutableListOf<Square>()

        (0..4).filter { it != 2 }.forEach { row ->
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

    fun coverPosition(square: Square): CoveredPiece? {
        return checkCoverPosition(square.col, square.row)
    }

    fun checkCoverPosition(col: Int, row: Int): CoveredPiece? {
        for (cover in coverSet) {
            if (col == cover.col && row == cover.row) {
                return cover
            }
        }
        return null
    }

    fun onGameEnd(): Int? {
        var winnerCode: Int? = null
        if (greenScore == 0 && purpleScore == 0) {
            winnerCode = 3
        } else if (purpleScore == 0 && redScore == 0) {
            winnerCode = 1
        } else if (redScore == 0 && greenScore == 0) {
            winnerCode = 2
        }
        return winnerCode
    }


}

