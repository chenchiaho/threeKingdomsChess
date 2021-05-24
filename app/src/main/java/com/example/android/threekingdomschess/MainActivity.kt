package com.example.android.threekingdomschess

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.android.threekingdomschess.databinding.ActivityMainBinding


private val TAG = "onClick"
class MainActivity : AppCompatActivity(), ChessDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<BoardView>(R.id.board_view).chessDelegate = this

        binding.restart.setOnClickListener {
            onGameClicked()
        }
        binding.intro.setOnClickListener { v: View ->

        }
    }

    private fun onGameClicked() {
        ChessGame.reset()
        findViewById<BoardView>(R.id.board_view).invalidate()
    }

    private fun navToInfo() {

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

