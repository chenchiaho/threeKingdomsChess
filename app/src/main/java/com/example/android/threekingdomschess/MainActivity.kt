package com.example.android.threekingdomschess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.threekingdomschess.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ChessDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<BoardView>(R.id.board_view).chessDelegate = this
    }

    override fun piecePosition(square: Square): ChessPiece? {
        return ChessGame.piecePosition(square)
    }

    override fun movePiece(from: Square, to: Square) {
//        if (fromCol == toCol && fromRow == toRow) {
//            return
//        }
        ChessGame.movePiece(from, to)
        findViewById<BoardView>(R.id.board_view).invalidate()
    }
}

