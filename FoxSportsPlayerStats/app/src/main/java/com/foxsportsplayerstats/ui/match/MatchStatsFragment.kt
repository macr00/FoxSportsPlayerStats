package com.foxsportsplayerstats.ui.match

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.app.injector
import com.foxsportsplayerstats.domain.model.MatchStatsModel
import com.foxsportsplayerstats.ui.UiView
import com.foxsportsplayerstats.ui.onDestroyObservable
import com.foxsportsplayerstats.ui.showErrorSnackbar

class MatchStatsFragment : Fragment(), UiView<MatchStatsModel>,
    TopPlayersAdapter.Listener {

    companion object {
        const val TAG = "MatchStatsFragment"
        @JvmStatic
        fun newInstance() = MatchStatsFragment()
    }

    private val viewModel: MatchStatsViewModel by lazy {
        ViewModelProvider(this, (activity!!.injector.matchStatsViewModelFactory()))
            .get(MatchStatsViewModel::class.java)
    }

    private val matchStatsAdapter = MatchStatsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_stats_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.match_stats_rv).run {
            this.layoutManager = LinearLayoutManager(view.context)
            this.adapter = matchStatsAdapter
            setHasFixedSize(true)
        }

        viewModel.matchStatsObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .doOnDispose { Log.d(TAG, "onDispose") }
            .subscribe({ state ->
                state.render(this)
            }) { t ->
                view.showErrorSnackbar(t) { viewModel.loadMatchStats() }
            }
    }

    override fun displayModel(model: MatchStatsModel) {
        val log = model.toString()
        Log.d(TAG, log)
        matchStatsAdapter.loadItems(model.matchStats)
        /*
        val textView = view?.findViewById<TextView>(R.id.log_text)
        textView?.visibility = View.VISIBLE
        textView?.text = log
        */

    }

    override fun displayLoading(isLoading: Boolean) {
        // TODO change loading view
        val textView = view?.findViewById<TextView>(R.id.log_text)
        if (isLoading) {
            textView?.visibility = View.VISIBLE
            textView?.text = "isLoading"
        } else {
            textView?.visibility = View.GONE
        }
    }

    override fun displayError(throwable: Throwable) {
        Log.e(TAG, throwable.localizedMessage ?: "Unknown error")
        view?.showErrorSnackbar(throwable) { viewModel.loadMatchStats() }
    }

    override fun onPlayerClicked(teamId: Int, playerId: Int) {
        activity?.let {
            Toast.makeText(it, "Team: $teamId, Player: $playerId", Toast.LENGTH_LONG).show()
        }
    }
}
