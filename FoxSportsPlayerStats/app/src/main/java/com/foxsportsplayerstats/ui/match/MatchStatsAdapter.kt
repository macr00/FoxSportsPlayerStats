package com.foxsportsplayerstats.ui.match

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.inflate
import com.foxsportsplayerstats.ui.layout.ListLayout

class MatchStatsAdapter : RecyclerView.Adapter<MatchStatsAdapter.ViewHolder>() {

    private var items: List<MatchStatModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.match_stat_item_layout, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun loadItems(items: List<MatchStatModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val statTitle = itemView.findViewById<TextView>(R.id.match_stat_type)
        private val teamAList = itemView.findViewById<ListLayout>(R.id.team_a_list)
        private val teamBList = itemView.findViewById<ListLayout>(R.id.team_b_list)

        fun bind(matchStatData: MatchStatModel) {
            with(matchStatData) {
                statTitle.text = matchStatData.statType.replace("_", " ")
                teamAList.setAdapter(TopPlayersStatsAdapter(itemView.context, teamA.topPlayerStats))
                teamBList.setAdapter(TopPlayersStatsAdapter(itemView.context, teamB.topPlayerStats, true))
            }
        }
    }

}