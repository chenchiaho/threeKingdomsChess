package com.example.android.a3kingdomschess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.android.a3kingdomschess.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

        val hasPiece: Boolean = false
        val isPieceCovered: Boolean = true

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun flipPiece (view: View) {

        val random = (0..1).random()

        val flipPieceAction: ImageView = view as ImageView
        flipPieceAction.setImageResource(
            when (random) {
                0 -> R.drawable.temp_pawn_black
                else -> R.drawable.temp_pawn_red
            })
    }


}