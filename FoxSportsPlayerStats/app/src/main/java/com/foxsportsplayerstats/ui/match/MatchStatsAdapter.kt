package com.foxsportsplayerstats.ui.match

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.MatchStat
import com.foxsportsplayerstats.network.TopPlayer

class MatchStatsAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<MatchStatsAdapter.ViewHolder>() {

    private val items: ArrayList<MatchStat> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.match_stat_item_layout, parent, false),
            listener = listener
        )
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

    class ViewHolder(
        itemView: View,
        private val listener: Listener
    ) : RecyclerView.ViewHolder(itemView) {

        private val statType = itemView.findViewById<TextView>(R.id.match_stat_type)
        private val playerA_1 = itemView.findViewById<LinearLayout>(R.id.team_a_player1)
        private val playerA_2 = itemView.findViewById<LinearLayout>(R.id.team_a_player2)
        private val playerA_3 = itemView.findViewById<LinearLayout>(R.id.team_a_player3)
        private val playerA_4 = itemView.findViewById<LinearLayout>(R.id.team_a_player4)
        private val playerA_5 = itemView.findViewById<LinearLayout>(R.id.team_a_player5)
        private val playerB_1 = itemView.findViewById<LinearLayout>(R.id.team_b_player1)
        private val playerB_2 = itemView.findViewById<LinearLayout>(R.id.team_b_player2)
        private val playerB_3 = itemView.findViewById<LinearLayout>(R.id.team_b_player3)
        private val playerB_4 = itemView.findViewById<LinearLayout>(R.id.team_b_player4)
        private val playerB_5 = itemView.findViewById<LinearLayout>(R.id.team_b_player5)

        //private val teamALayout = itemView.findViewById<LinearLayout>(R.id.team_a_players)
        //private val teamAListView = itemView.findViewById<ListView>(R.id.team_a_top_players)

        fun bind(matchStat: MatchStat) {
            Log.d("ViewHolder", matchStat.toString())
            statType.text = matchStat.statType

            with(matchStat.teamA) {
                displayTopPlayerStat(topPlayers[0], playerA_1)
                displayTopPlayerStat(topPlayers[1], playerA_2)
                displayTopPlayerStat(topPlayers[2], playerA_3)
                displayTopPlayerStat(topPlayers[3], playerA_4)
                displayTopPlayerStat(topPlayers[4], playerA_5)

            }

            with(matchStat.teamB) {
                displayTopPlayerStat(topPlayers[0], playerB_1)
                displayTopPlayerStat(topPlayers[1], playerB_2)
                displayTopPlayerStat(topPlayers[2], playerB_3)
                displayTopPlayerStat(topPlayers[3], playerB_4)
                displayTopPlayerStat(topPlayers[4], playerB_5)
            }


            //teamAListView.adapter = MatchTopPlayersAdapter(matchStat.teamA.topPlayers, itemView.context)

            /*
            val teamId = matchStat.teamA.id
            val playerId = matchStat.teamA.topPlayers[0].id
            itemView.setOnClickListener {
                listener.onPlayerClicked(teamId, playerId)
            }
             */
        }

        private fun displayTopPlayerStat(player: TopPlayer, view: View) {
            view.findViewById<TextView>(R.id.top_player_position).text = player.position
            view.findViewById<TextView>(R.id.top_player_jumper).text = player.jumperNumber
            view.findViewById<TextView>(R.id.top_player_name).text = player.shortName
        }
    }

    interface Listener {

        fun onPlayerClicked(teamId: Int, playerId: Int)
    }
 }