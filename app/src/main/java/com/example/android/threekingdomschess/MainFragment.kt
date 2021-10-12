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
import com.example.android.threekingdomschess.model.CoveredPiece
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
                        winner = when (ChessGame.onGameEnd()) {
                            1 -> getString(R.string.winner_green)
                            2 -> getString(R.string.winner_purple)
                            else -> getString(R.string.winner_red)
                        }
                        val builder = AlertDialog.Builder(requireContext())
                                .setTitle("")
                                .setMessage(winner)
                                .setPositiveButton(getString(R.string.good_game)) { dialogInterface: DialogInterface, i: Int ->
                                    onGameClicked(binding)
                                    val nextPlay = ChessGame.nextTurn()
                                    playerIndicator(nextPlay)
                                }
                                .setNegativeButton(getString(R.string.dismiss)) { dialogInterface: DialogInterface, i: Int ->

                                }.create()
                        builder.show()
                    }
                    return true
                }
                return true
            }
        }
        )

        binding.styleSwitch.setOnClickListener {
            styleSwap(binding)
        }

        binding.restart.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
                    .setTitle("")
                    .setMessage(getString(R.string.restart_game))
                    .setPositiveButton(getString(R.string.restart_confirm)) { dialogInterface: DialogInterface, i: Int ->
                        onGameClicked(binding)
                        val next = ChessGame.nextTurn()
                        playerIndicator(next)
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialogInterface: DialogInterface, i: Int ->

                    }.create()
            builder.show()
        }
        binding.intro.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToInfoFragment())
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val nextPlayer = ChessGame.nextTurn()
        playerIndicator(nextPlayer)
    }

    private fun styleSwap(binding: FragmentMainBinding) {
        ChessGame.switchStyle()
        binding.boardView.invalidate()
    }

    private fun onGameClicked(binding: FragmentMainBinding) {
        ChessGame.reset()
        binding.boardView.invalidate()
    }

    override fun assignCoverPosition(square: Square): CoveredPiece? {
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
            duration = 500
            alpha(1.0f)
        }.start()
    }
    private fun fadeOutAnimate(imageView: ImageView) {
        imageView.animate().apply {
            duration = 500
            alpha(0.0f)
        }.start()
    }

    fun playerIndicator(next: Player) {
        when (next) {
            Player.GREEN -> {
                fadeInAnimate(indicator_1)
                fadeOutAnimate(indicator_2)
                fadeOutAnimate(indicator_3)
            }
            Player.PURPLE -> {
                fadeInAnimate(indicator_2)
                fadeOutAnimate(indicator_1)
                fadeOutAnimate(indicator_3)
            }
            Player.RED -> {
                fadeInAnimate(indicator_3)
                fadeOutAnimate(indicator_1)
                fadeOutAnimate(indicator_2)
            }
        }
    }
}

