package com.example.android.threekingdomschess

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.threekingdomschess.databinding.FragmentMainBinding


class MainFragment : Fragment(), ChessDelegate {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.boardView.chessDelegate = this

        binding.restart.setOnClickListener {
            onGameClicked(binding)
        }
        binding.intro.setOnClickListener { view: View ->
            view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToInfoFragment())
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
