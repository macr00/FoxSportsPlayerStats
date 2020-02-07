package com.foxsportsplayerstats.ui.match

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.MatchStat
import com.foxsportsplayerstats.network.TopPlayer

class MatchStatsAdapter(
    private val listener: TopPlayersAdapter.Listener
) : RecyclerView.Adapter<MatchStatsAdapter.ViewHolder>() {

    private val items: ArrayList<MatchStat> = arrayListOf()
    private val pool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match_stat_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], pool, listener)
    }

    fun loadItems(items: List<MatchStat>) {
        this.items.run {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val rvTeamA = itemView.findViewById<RecyclerView>(R.id.teamA_top_players_rv)
        private val rvTeamB = itemView.findViewById<RecyclerView>(R.id.teamB_top_players_rv)

        private val statType = itemView.findViewById<TextView>(R.id.match_stat_type)

        fun bind(
            matchStat: MatchStat,
            pool: RecyclerView.RecycledViewPool,
            listener: TopPlayersAdapter.Listener
        ) {
            Log.d("ViewHolder", matchStat.toString())
            statType.text = matchStat.statType

            setUpRecycler(rvTeamA, matchStat.teamA.topPlayers, pool, listener)
            setUpRecycler(rvTeamB, matchStat.teamB.topPlayers, pool, listener)
        }

        private fun setUpRecycler(
            recyclerView: RecyclerView,
            players: List<TopPlayer>,
            pool: RecyclerView.RecycledViewPool,
            listener: TopPlayersAdapter.Listener
        ) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
                adapter = TopPlayersAdapter(players, listener)
                setRecycledViewPool(pool)
            }
        }
    }

}