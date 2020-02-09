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
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.ui.*
import com.foxsportsplayerstats.ui.layout.ProgressRetryLayout

class MatchStatsFragment : Fragment(), UiView<List<MatchStatModel>> {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            viewModel.loadMatchStats()
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

        view.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)
            .setRetryClickListener { viewModel.loadMatchStats() }

        view.findViewById<RecyclerView>(R.id.match_stats_rv).run {
            this.layoutManager = LinearLayoutManager(view.context)
            this.adapter = matchStatsAdapter
        }

        viewModel.viewStateObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .subscribe({ state ->
                state.render(this)
            }) { throwable ->
                displayError(throwable)
            }
    }

    override fun displayModel(model: List<MatchStatModel>) {
        matchStatsAdapter.loadItems(model)
    }

    override fun displayProgress() {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.showProgress()
    }

    override fun displayError(throwable: Throwable) {
        view?.run {
            findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.showRetry()
            showErrorSnackbar(throwable)
        }
    }

    override fun hideProgressRetry() {
        view?.findViewById<ProgressRetryLayout>(R.id.progress_retry_layout)?.gone()
    }
}
