package com.example.android.threekingdomschess

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.threekingdomschess.databinding.FragmentMainBinding
import com.example.android.threekingdomschess.model.Square
import com.example.android.threekingdomschess.model.ChessPiece
import com.example.android.threekingdomschess.model.Cover
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

                    val isCovered = binding.boardView.isCovered(event)

                    if (isCovered) {
                        binding.boardView.onInitialTouchEvent(event)
                        val next = ChessGame.nextTurn()
                        playerIndicator(next)
                        pieceSelected = false

                    } else if (!isCovered) {
                        binding.boardView.onFirstTouchEvent(event)
                        val col = binding.boardView.returnSquare().first
                        val row = binding.boardView.returnSquare().second
                        pieceSelected = assignPiecePosition(Square(col, row)) != null
                    }
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

    private fun styleSwap(binding: FragmentMainBinding) {
        ChessGame.switchStyle()
        binding.boardView.invalidate()
    }

    private fun onGameClicked(binding: FragmentMainBinding) {
        ChessGame.reset()
        binding.boardView.invalidate()
    }

    override fun assignCoverPosition(square: Square): Cover? {
        return ChessGame.coverPosition(square)
    }

    override fun removeCover(fromCol: Int, fromRow: Int) {
        ChessGame.turnPiece(fromCol, fromRow)
    }

    override fun assignPiecePosition(square: Square): ChessPiece? {
        return ChessGame.piecePositionSquare(square)
    }

    override fun movePiece(from: Square, to: Square) {
        ChessGame.movePiece(from, to)
    }

    private fun fadeInAnimate(imageView: ImageView) {
        imageView.animate().apply {
            duration = 600
            alpha(1.0f)

        }.start()
    }
    private fun fadeOutAnimate(imageView: ImageView) {
        imageView.animate().apply {
            duration = 600
            alpha(0.0f)
        }.start()
    }


    fun playerIndicator(next: Player) {
        when (next) {
            Player.GREEN -> {
                fadeInAnimate(zhou1)
                fadeOutAnimate(zhou2)
                fadeOutAnimate(zhou3)
            }
            Player.BLACK -> {
                fadeInAnimate(zhou2)
                fadeOutAnimate(zhou1)
                fadeOutAnimate(zhou3)
            }
            Player.RED -> {
                fadeInAnimate(zhou3)
                fadeOutAnimate(zhou1)
                fadeOutAnimate(zhou2)
            }
        }
    }

}

