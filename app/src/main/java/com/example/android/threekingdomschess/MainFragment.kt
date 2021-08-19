package com.example.android.threekingdomschess

import android.app.Dialog
import android.content.DialogInterface
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.threekingdomschess.databinding.FragmentMainBinding
import com.example.android.threekingdomschess.model.Player
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.pieces.ChessPiece


class MainFragment : Fragment(), ChessDelegate {

    var xiang : ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.boardView.chessDelegate = this

        xiang = binding.zhou
        xiang?.alpha = 0.55f


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

    override fun assignPiecePosition(square: Square): ChessPiece? {
        return ChessGame.piecePositionSquare(square)
    }

    override fun movePiece(from: Square, to: Square) {
        ChessGame.movePiece(from, to)

        }



    fun gameEndDialog(winner: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("")
        builder.setMessage("$winner WON!")
        builder.setPositiveButton("GG") { dialogInterface: DialogInterface, i: Int ->
            ChessGame.reset()
        }
        builder.setNegativeButton("Dismiss") { dialogInterface: DialogInterface, i: Int ->

        }
        builder.show()
    }
}




//    private fun gameEndDialog() {
//        val dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(true)
//        dialog.setContentView(R.layout.custom_layout)
//        val body = dialog.findViewById(R.id.body) as TextView
//        body.text = title
//        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//        yesBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }
//        dialog.show()
//
//    }

