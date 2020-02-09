package com.foxsportsplayerstats.ui.match

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.foxsportsplayerstats.R
import com.foxsportsplayerstats.app.injector
import com.foxsportsplayerstats.domain.model.MatchStatsModel
import com.foxsportsplayerstats.ui.UiView
import com.foxsportsplayerstats.ui.layout.ProgressRetryLayout
import com.foxsportsplayerstats.ui.onDestroyObservable
import com.foxsportsplayerstats.ui.showErrorSnackbar
import com.foxsportsplayerstats.ui.visibleOrGone

class MatchStatsFragment : Fragment(), UiView<MatchStatsModel> {

    companion object {
        const val TAG = "MatchStatsFragment"
        @JvmStatic
        fun newInstance() = MatchStatsFragment()
    }

    private val matchStatsAdapter = MatchStatsAdapter()

    private val viewModel: MatchStatsViewModel by lazy {
        ViewModelProvider(this, (activity!!.injector.matchStatsViewModelFactory()))
            .get(MatchStatsViewModel::class.java)
    }

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

        view.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout).setRetryClickListener {
            viewModel.loadMatchStats()
        }

        view.findViewById<RecyclerView>(R.id.match_stats_rv).run {
            this.layoutManager = LinearLayoutManager(view.context)
            this.adapter = matchStatsAdapter
        }

        viewModel.matchStatsObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .subscribe({ state ->
                state.render(this)
            }) { throwable ->
                view.showErrorSnackbar(throwable)
            }
    }

    override fun displayModel(model: MatchStatsModel) {
        matchStatsAdapter.loadItems(model.matchStats)
    }

    override fun displayProgress(isLoading: Boolean) {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.showProgress(isLoading)
    }

    override fun displayError(throwable: Throwable) {
        view?.run {
            findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.showRetry(true)
            showErrorSnackbar(throwable)
        }
    }

    override fun hideProgressRetry() {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.visibleOrGone(false)
    }
}
