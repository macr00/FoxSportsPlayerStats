package com.foxsportsplayerstats.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.app.injector
import com.foxsportsplayerstats.domain.model.PlayerStatsModel
import com.foxsportsplayerstats.network.FoxSportsApi
import com.foxsportsplayerstats.ui.*
import com.foxsportsplayerstats.ui.layout.ProgressRetryLayout

class PlayerStatsFragment : Fragment(), UiView<PlayerStatsModel> {

    companion object {
        const val TAG = "PlayerStatsFragment"

        private const val TEAM_ID_PARAM = "teamId"
        private const val PLAYER_ID_PARAM = "playerId"

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

        view.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout).setRetryClickListener {
            loadPlayerStats()
        }

        viewModel.playerStatsObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .subscribe({ state ->
                state.render(this)
            }) { throwable ->
                view.showErrorSnackbar(throwable)
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
        view?.run {
            findViewById<TextView>(R.id.player_full_name)?.text = model.data.fullName
            findViewById<TextView>(R.id.player_position)?.text = model.data.position
            loadPlayerHeadShot(
                findViewById(R.id.player_head_shot),
                FoxSportsApi.getImgUrl(model.data.id)
            )
        }
    }

    override fun displayProgress(isLoading: Boolean) {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.showProgress(isLoading)
    }

    override fun displayError(throwable: Throwable) {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.showRetry(true)
        view?.showErrorSnackbar(throwable)
    }

    override fun hideProgressRetry() {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.visibleOrGone(false)
    }
}
