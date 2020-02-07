package com.foxsportsplayerstats.ui.match

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.TopPlayer

class TopPlayersAdapter(
    private val items: List<TopPlayer>,
    private val listener: Listener
) : RecyclerView.Adapter<TopPlayersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.top_player_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val shortName = itemView.findViewById<TextView>(R.id.top_player_name)
        private val jumperNo = itemView.findViewById<TextView>(R.id.top_player_jumper)
        private val playerPosition = itemView.findViewById<TextView>(R.id.top_player_position)

        fun bind(player: TopPlayer) {
            shortName.text = player.shortName
            jumperNo.text = player.jumperNumber
            playerPosition.text = player.position
        }
    }

    interface Listener {

        fun onPlayerClicked(teamId: Int, playerId: Int)
    }
}