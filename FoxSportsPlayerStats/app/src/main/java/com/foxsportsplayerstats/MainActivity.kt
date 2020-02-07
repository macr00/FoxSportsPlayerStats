package com.foxsportsplayerstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.foxsportsplayerstats.ui.match.MatchStatsFragment
import com.foxsportsplayerstats.ui.match.TopPlayersListAdapter

class MainActivity : AppCompatActivity(), TopPlayersListAdapter.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_layout, MatchStatsFragment.newInstance())
                .commitNow()
        }
    }

    override fun onPlayerClicked(teamId: Int, playerId: Int) {
        Toast.makeText(this, "Team: $teamId, Player: $playerId", Toast.LENGTH_LONG).show()

        // TODO add PlayerStatsFragment to stack
    }
}
