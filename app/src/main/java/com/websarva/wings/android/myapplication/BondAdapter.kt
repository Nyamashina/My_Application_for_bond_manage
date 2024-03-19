package com.websarva.wings.android.myapplication

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


//item_bond.xml用
class BondAdapter(private val bondList: List<Bond>,private val onItemClicked: (Bond) -> Unit) : RecyclerView.Adapter<BondViewHolder>() {
    var selectedPosition = RecyclerView.NO_POSITION

    override fun getItemCount(): Int {
        return bondList.size
    }

    fun clearSelection() {
        Log.d("Adapter","clearSelection")
        if (selectedPosition != RecyclerView.NO_POSITION) {
            val previousSelectedPosition = selectedPosition
            selectedPosition = RecyclerView.NO_POSITION
            notifyItemChanged(previousSelectedPosition)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BondViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bond, parent, false)
        Log.d("Adapter","onCreateViewHolder")
        return BondViewHolder(view)
    }

    /*
    override fun onBindViewHolder(holder: BondViewHolder, position: Int) {
        Log.d("Adapter","onBindViewHolder")
        val bond = bondList[position]
        holder.bind(bond)

        // アイテムの選択状態を設定
        holder.itemView.setBackgroundColor(if (selectedPosition == position) Color.LTGRAY else Color.TRANSPARENT)

        // アイテムのクリックイベント
        holder.itemView.setOnClickListener {
            Log.d("Adapter","onBindViewHolder on click,position:${position}")
            // 以前の選択をクリア
            clearSelection()

            val currentPosition = holder.adapterPosition// 現在のアイテムビューの位置を取得
            val selectedBond = bondList[currentPosition]

            onItemClicked(selectedBond)
        }
    }

     */

    /* ViewHolder内で長押しイベントを設定
    inner class LongClickViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {
                onItemLongClick(bondList[adapterPosition])
                true
            }
        }
    }*/

}