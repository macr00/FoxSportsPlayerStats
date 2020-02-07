package com.foxsportsplayerstats.ui.match

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.network.TopPlayer

class MatchTopPlayersAdapter(private val items: List<TopPlayer>, context: Context) :
    ArrayAdapter<TopPlayer>(context, R.layout.top_player_list_item, items) {

    companion object {
        const val TAG = "TopPlayersAdapter"
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        val viewHolder: ViewHolder

        // Inflate view and create ViewHolder if null
        if (v == null) {
            v = LayoutInflater.from(parent.context).inflate(R.layout.top_player_list_item, parent, false)

            viewHolder = ViewHolder().apply {
                imageView = v.findViewById(R.id.top_player_image)
                shortName = v.findViewById(R.id.top_player_name)
                jumperNo = v.findViewById(R.id.top_player_jumper)
                playerPosition = v.findViewById(R.id.top_player_position)
            }

            v.tag = viewHolder

        } else {
            viewHolder = v.tag as ViewHolder
        }

        // Populate view holder with TopPlayer item
        val topPlayer = getItem(position)!!
        Log.d(TAG, topPlayer.toString())
        with(viewHolder) {
            shortName.text = topPlayer.shortName
            jumperNo.text = topPlayer.jumperNumber
            playerPosition.text = topPlayer.position
        }

        return v!!
    }

    override fun getCount(): Int {
        return items.size
    }

    class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var shortName: TextView
        lateinit var jumperNo: TextView
        lateinit var playerPosition: TextView
    }
}
