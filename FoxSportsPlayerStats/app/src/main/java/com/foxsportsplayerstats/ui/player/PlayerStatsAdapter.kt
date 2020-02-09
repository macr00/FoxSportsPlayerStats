package com.foxsportsplayerstats.ui.player

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.ui.capitalizeWords
import com.foxsportsplayerstats.ui.inflate

class PlayerStatsAdapter : RecyclerView.Adapter<PlayerStatsAdapter.ViewHolder>() {

    private var items: List<Pair<String, Int>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.player_stat_cell, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
            val color = if (position % 2 == 0) R.color.colorGrey else android.R.color.white
            itemView.run {
                setBackgroundColor(ContextCompat.getColor(context, color))
            }
        }
    }

    fun loadItems(items: List<Pair<String, Int>>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val key = itemView.findViewById<TextView>(R.id.stat_label)
        private val value = itemView.findViewById<TextView>(R.id.stat_value)

        fun bind(pair: Pair<String, Int>) {
            key.text = pair.first.capitalizeWords()
            value.text = pair.second.toString()
        }
    }
}