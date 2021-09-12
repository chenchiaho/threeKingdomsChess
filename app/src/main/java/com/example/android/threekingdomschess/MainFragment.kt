package com.example.android.threekingdomschess

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.threekingdomschess.databinding.FragmentMainBinding
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.model.ChessPiece
import com.example.android.threekingdomschess.model.Player
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), ChessDelegate {


    private var pieceSelected = false
    var winner: String? = null


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.boardView.chessDelegate = this

        binding.boardView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                event ?: return false

                if (!pieceSelected && event.action == MotionEvent.ACTION_UP) {
                    binding.boardView.onFirstTouchEvent(event)
                    pieceSelected = true
                    return true
                }

                if (pieceSelected && event.action == MotionEvent.ACTION_UP) {

                    binding.boardView.onSecondTouchEvent(event)
                    val next = ChessGame.nextTurn()
                    playerIndicator(next)

                    pieceSelected = false

                    if (ChessGame.onGameEnd() != null) {
                        winner = ChessGame.onGameEnd()
                        val builder = AlertDialog.Builder(requireContext())
                                .setTitle("")
                                .setMessage("$winner WON!")
                                .setPositiveButton("GG") { dialogInterface: DialogInterface, i: Int ->
                                    ChessGame.reset()
                                }
                                .setNegativeButton("Dismiss") { dialogInterface: DialogInterface, i: Int ->

                                }.create()
                        builder.show()
                    }

                    return true
                }
                return true

            }

        }


        )

        binding.toggle.setOnClickListener() {
            styleSwap(binding)
        }

        binding.restart.setOnClickListener {

            onGameClicked(binding)
            val next = ChessGame.nextTurn()
            playerIndicator(next)
        }
        binding.intro.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToInfoFragment())
        }


        return binding.root
    }

    fun styleSwap(binding: FragmentMainBinding) {
        ChessGame.switchStyle()
        binding.boardView.invalidate()
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


    fun playerIndicator(next: Player) {
        when (next) {
            Player.GREEN -> {
                zhou1.isVisible = true
                zhou2.isVisible = false
                zhou3.isVisible = false
            }
            Player.BLACK -> {
                zhou2.isVisible = true
                zhou1.isVisible = false
                zhou3.isVisible = false
            }
            Player.RED -> {
                zhou3.isVisible = true
                zhou1.isVisible = false
                zhou2.isVisible = false
            }
        }
    }

}

