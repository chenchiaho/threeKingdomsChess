package com.example.android.threekingdomschess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.android.threekingdomschess.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ChessDelegate {

    var chessModel = ChessModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "$chessModel")
        val boardView = findViewById<BoardView>(R.id.board_view)
        boardView.chessDelegate = this
    }

    override fun piecePosition(col: Int, row: Int): ChessPiece? {
        return chessModel.piecePosition(col, row)
    }
}

