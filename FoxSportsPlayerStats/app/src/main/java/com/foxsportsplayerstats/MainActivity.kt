package com.foxsportsplayerstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.foxsportsplayerstats.ui.match.MatchStatsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_layout, MatchStatsFragment.newInstance())
                .commitNow()
        }
    }
}
