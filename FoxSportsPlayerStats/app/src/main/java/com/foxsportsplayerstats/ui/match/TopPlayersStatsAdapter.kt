package com.foxsportsplayerstats.ui.match

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.domain.model.TopPlayerStatModel
import com.foxsportsplayerstats.data.network.FoxSportsApi
import com.foxsportsplayerstats.ui.displayNameAndJumperNo
import com.foxsportsplayerstats.ui.inflate
import com.foxsportsplayerstats.ui.loadPlayerHeadShot

class TopPlayersStatsAdapter(
    context: Context,
    private val items: List<TopPlayerStatModel>,
    private val rtl: Boolean = false
) : ArrayAdapter<TopPlayerStatModel>(context, 0, items) {

    companion object {
        const val TAG = "TopPlayersListAdapter"
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        val viewHolder: ViewHolder

        // Inflate view and create ViewHolder if null
        if (v == null) {
            v = parent.inflate(R.layout.top_player_item, false)
            viewHolder = ViewHolder().apply {
                imageView = v.findViewById(R.id.top_player_image)
                statView = v.findViewById(R.id.top_player_stat_value)
                nameNumberView = v.findViewById(R.id.top_player_name_number)
                playerPositionView = v.findViewById(R.id.top_player_position)
            }
            if(rtl && v is ViewGroup) v.layoutDirection = View.LAYOUT_DIRECTION_RTL
            v.tag = viewHolder
        } else {
            viewHolder = v.tag as ViewHolder
        }

        // Populate view holder with TopPlayer item
        val topPlayer = getItem(position)!!
        val id = topPlayer.id

        with(viewHolder) {
            statView.text = topPlayer.statValue
            playerPositionView.text = topPlayer.position
            nameNumberView.displayNameAndJumperNo(topPlayer)
            parent.loadPlayerHeadShot(imageView, FoxSportsApi.getImgUrl(id))
        }

        // Set onClick listener
        if (parent.context !is Listener) {
            Log.d(TAG, "Activity must implement TopPlayersListAdapter.Listener")
        } else {
            v.setOnClickListener {
                (parent.context as Listener).onPlayerClicked(topPlayer.teamId, topPlayer.id)
            }
        }

        return v
    }

    override fun getCount(): Int {
        return items.size
    }

    class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var statView: TextView
        lateinit var nameNumberView: TextView
        lateinit var playerPositionView: TextView
    }

    interface Listener {
        fun onPlayerClicked(teamId: Int, playerId: Int)
    }
}