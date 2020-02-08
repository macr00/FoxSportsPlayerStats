package com.foxsportsplayerstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.foxsportsplayerstats.ui.match.MatchStatsFragment
import com.foxsportsplayerstats.ui.match.TopPlayersListAdapter
import com.foxsportsplayerstats.ui.player.PlayerStatsFragment

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
        supportFragmentManager.beginTransaction()
            .add(R.id.container_layout, PlayerStatsFragment.newInstance(teamId, playerId))
            .addToBackStack(PlayerStatsFragment.TAG)
            .run { commit() }
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
