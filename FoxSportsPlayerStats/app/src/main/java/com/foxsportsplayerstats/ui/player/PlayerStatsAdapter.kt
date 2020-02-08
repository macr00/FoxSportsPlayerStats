package com.foxsportsplayerstats.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.ui.capitalizeWords

class PlayerStatsAdapter() : RecyclerView.Adapter<PlayerStatsAdapter.ViewHolder>() {

    private var items: List<Pair<String, Int>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.player_stat_cell, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position % 2 == 0)
    }

    fun loadItems(items: List<Pair<String, Int>>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val key = itemView.findViewById<TextView>(R.id.stat_label)
        private val value = itemView.findViewById<TextView>(R.id.stat_value)

        fun bind(pair: Pair<String, Int>, shadeBackground: Boolean) {
            key.text = pair.first.capitalizeWords()
            value.text = pair.second.toString()
            if (shadeBackground) {
                with(itemView) {
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey))
                }
            }
        }
    }
}