package com.example.android.threekingdomschess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.threekingdomschess.databinding.FragmentMainBinding
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.pieces.ChessPiece


class MainFragment : Fragment(), ChessDelegate {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.boardView.chessDelegate = this

        binding.restart.setOnClickListener {
            onGameClicked(binding)
        }
        binding.intro.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToInfoFragment())
        }
        return binding.root
    }

    private fun onGameClicked(binding: FragmentMainBinding) {
        ChessGame.reset()
        binding.boardView.invalidate()
    }

    override fun piecePosition(square: Square): ChessPiece? {
        return ChessGame.piecePosition(square)
    }

    override fun movePiece(from: Square, to: Square) {
        ChessGame.movePiece(from, to)
    }

}
