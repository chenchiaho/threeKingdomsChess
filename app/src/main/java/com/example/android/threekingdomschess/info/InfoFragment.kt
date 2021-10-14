package com.example.android.threekingdomschess.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.threekingdomschess.R
import com.example.android.threekingdomschess.databinding.FragmentInfoBinding
import kotlinx.android.synthetic.main.instruction_item.*

class InfoFragment : Fragment(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var instructionList: ArrayList<Instructions>
    lateinit var titleId: Array<Int>
    lateinit var titleText: Array<String>
    lateinit var ruleId: Array<Int>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentInfoBinding.inflate(inflater)

        titleId = arrayOf(
                R.drawable.title_king,
                R.drawable.title_pawn,
                R.drawable.title_rook,
                R.drawable.title_horse,
                R.drawable.title_cannon,
                R.drawable.title_guard,
                R.drawable.title_elephant
        )
        titleText = arrayOf(
                getString(R.string.king_set),
                getString(R.string.pawn_set),
                getString(R.string.rook_set),
                getString(R.string.knight_set),
                getString(R.string.cannon_set),
                getString(R.string.guard_set),
                getString(R.string.bishop_set)
        )
        ruleId = arrayOf(
                R.drawable.king_rule,
                R.drawable.pawn_rule,
                R.drawable.rook_rule,
                R.drawable.horse_rule,
                R.drawable.cannon_rule,
                R.drawable.guard_rule,
                R.drawable.elephant_rule
        )

        recyclerView = binding.ruleRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        instructionList = arrayListOf()
        getInstructionData()

        return binding.root
    }

    private fun getInstructionData() {

        for (i in titleId.indices) {
            val instructions = Instructions(titleId[i], titleText[i], ruleId[i])
            instructionList.add(instructions)
        }

        recyclerView.adapter = InstructionAdapter(instructionList)
    }
}