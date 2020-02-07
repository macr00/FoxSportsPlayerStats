package com.foxsportsplayerstats.ui.match

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.FoxSportsApi
import com.foxsportsplayerstats.network.TopPlayer

class TopPlayersAdapter(
    private val items: List<TopPlayer>,
    private val teamId: Int,
    private val listener: Listener
) : RecyclerView.Adapter<TopPlayersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.top_player_item, parent, false),
            teamId = teamId,
            listener = listener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(
        itemView: View,
        private val teamId: Int,
        private val listener: Listener
    ) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.findViewById<ImageView>(R.id.top_player_image)
        private val statValue = itemView.findViewById<TextView>(R.id.top_player_stat_value)
        private val shortName = itemView.findViewById<TextView>(R.id.top_player_name_number)
        private val playerPosition = itemView.findViewById<TextView>(R.id.top_player_position)

        fun bind(player: TopPlayer) {
            statValue.text = player.statValue
            shortName.text = itemView.context.resources
                .getString(R.string.player_name_jumper, player.jumperNumber, player.shortName)
            playerPosition.text = player.position
            imageView.setOnClickListener { listener.onPlayerClicked(teamId, player.id) }
            loadImage(player.id)
        }

        private fun loadImage(id: Int) {
            // TODO Glide error callback load url
            Glide.with(itemView)
                .load(FoxSportsApi.IMG_URL + "$id.jpg")
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(imageView)
        }
    }

    interface Listener {
        fun onPlayerClicked(teamId: Int, playerId: Int)
    }
}