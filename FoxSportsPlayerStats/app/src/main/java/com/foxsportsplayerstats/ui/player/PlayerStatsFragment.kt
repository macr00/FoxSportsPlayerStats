package com.foxsportsplayerstats.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.app.injector
import com.foxsportsplayerstats.domain.model.PlayerStatsModel
import com.foxsportsplayerstats.ui.UiView
import com.foxsportsplayerstats.ui.match.MatchStatsFragment
import com.foxsportsplayerstats.ui.onDestroyObservable
import com.foxsportsplayerstats.ui.showErrorSnackbar
import com.foxsportsplayerstats.ui.visibleOrGone

private const val TEAM_ID_PARAM = "teamId"
private const val PLAYER_ID_PARAM = "playerId"

class PlayerStatsFragment : Fragment(), UiView<PlayerStatsModel> {

    companion object {
        const val TAG = "PlayerStatsFragment"
        @JvmStatic
        fun newInstance(teamId: Int, playerId: Int) =
            PlayerStatsFragment().apply {
                arguments = Bundle().apply {
                    putInt(TEAM_ID_PARAM, teamId)
                    putInt(PLAYER_ID_PARAM, playerId)
                }
            }
    }

    private val viewModel: PlayerStatsViewModel by lazy {
        ViewModelProvider(this, (activity!!.injector.playerStatsViewModelFactory()))
            .get(PlayerStatsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadPlayerStats()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_stats, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playerStatsObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .subscribe({ state ->
                state.render(this)
            }) { t ->
                view.showErrorSnackbar(t) { loadPlayerStats() }
            }
    }

    private fun loadPlayerStats() {
        arguments?.let {
            val teamId = it.getInt(TEAM_ID_PARAM)
            val playerId = it.getInt(PLAYER_ID_PARAM)
            viewModel.loadPlayer(teamId, playerId)
        }
    }

    override fun displayModel(model: PlayerStatsModel) {
        val log = model.toString()
        Log.d(TAG, log)
        val textView = view?.findViewById<TextView>(R.id.player_log)
        textView?.visibility = View.VISIBLE
        textView?.text = log
    }

    override fun displayLoading(isLoading: Boolean) {
        view?.findViewById<FrameLayout>(R.id.loading_frame)?.visibleOrGone(isLoading)
    }

    override fun displayError(throwable: Throwable) {
        view?.showErrorSnackbar(throwable) { loadPlayerStats() }
    }
}
