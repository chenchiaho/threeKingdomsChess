package com.example.android.threekingdomschess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.android.threekingdomschess.databinding.ActivityMainBinding

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ChessDelegate {

    var chessModel = ChessModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findViewById<BoardView>(R.id.board_view).chessDelegate = this
    }

    override fun piecePosition(col: Int, row: Int): ChessPiece? {
        return chessModel.piecePosition(col, row)
    }

    override fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        chessModel.movePiece(fromCol, fromRow, toCol, toRow)
        findViewById<BoardView>(R.id.board_view).invalidate()
    }
}

