package com.example.android.threekingdomschess.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.threekingdomschess.R
import kotlinx.android.synthetic.main.instruction_item.view.*

class InstructionAdapter(private val instructionList: ArrayList<Instructions>)
    : RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.instruction_item,
        parent, false)

        return InstructionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        val currentItem = instructionList[position]
        holder.titleImage.setImageResource(currentItem.title)
        holder.titleText.text = currentItem.name
        holder.ruleImage.setImageResource(currentItem.rules)
    }

    override fun getItemCount(): Int {
        return instructionList.size
    }

    class InstructionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val titleImage: ImageView = itemView.findViewById(R.id.titleImage)
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val ruleImage: ImageView = itemView.findViewById(R.id.ruleImage)
    }
}