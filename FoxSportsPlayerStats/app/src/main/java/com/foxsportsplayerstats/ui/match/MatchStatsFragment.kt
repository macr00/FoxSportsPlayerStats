package com.foxsportsplayerstats.ui.match

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
import com.foxsportsplayerstats.ui.visibleOrGone

class MatchStatsFragment : Fragment(), UiView<MatchStatsModel> {

    companion object {
        const val TAG = "MatchStatsFragment"
        @JvmStatic
        fun newInstance() = MatchStatsFragment()
    }

    private val viewModel: MatchStatsViewModel by lazy {
        ViewModelProvider(this, (activity!!.injector.matchStatsViewModelFactory()))
            .get(MatchStatsViewModel::class.java)
    }

    private val matchStatsAdapter = MatchStatsAdapter()

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
        Log.d(TAG, model.toString())
        matchStatsAdapter.loadItems(model.matchStats)
    }

    override fun displayLoading(isLoading: Boolean) {
        view?.findViewById<FrameLayout>(R.id.loading)?.visibleOrGone(isLoading)
    }

    override fun displayError(throwable: Throwable) {
        view?.showErrorSnackbar(throwable) { viewModel.loadMatchStats() }
    }
}
