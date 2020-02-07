package com.foxsportsplayerstats.ui.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.MatchStat
import com.foxsportsplayerstats.ui.layout.ListLayout

class MatchStatsAdapter : RecyclerView.Adapter<MatchStatsAdapter.ViewHolder>() {

    private val items: ArrayList<MatchStat> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match_stat_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun loadItems(items: List<MatchStat>) {
        this.items.run {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val statTitle = itemView.findViewById<TextView>(R.id.match_stat_type)
        private val teamAList = itemView.findViewById<ListLayout>(R.id.team_a_list)
        private val teamBList = itemView.findViewById<ListLayout>(R.id.team_b_list)

        fun bind(matchStat: MatchStat) {
            with(matchStat) {
                statTitle.text = matchStat.statType.replace("_", " ")
                teamAList.setAdapter(TopPlayersListAdapter(teamA.topPlayers, teamA.id, itemView.context))
                teamBList.setAdapter(TopPlayersListAdapter(teamB.topPlayers, teamB.id, itemView.context))
            }
        }
    }

}