package com.foxsportsplayerstats.ui.match

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
import com.foxsportsplayerstats.ui.onDestroyObservable
import com.google.android.material.snackbar.Snackbar

class MatchStatsFragment : Fragment() {

    companion object {
        const val TAG = "MatchStatsFragment"
        fun newInstance() = MatchStatsFragment()
    }

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

        viewModel.matchStatsObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .doOnDispose { Log.d(TAG, "onDispose") }
            .subscribe({ state ->
                Log.d(TAG, state.log)
                view.findViewById<TextView>(R.id.log_text).text = state.log
            }) { t ->
                Log.e(TAG, t.localizedMessage ?: "Unknown error")
                showErrorSnackBar(view, t)
            }

        viewModel.errorObservable()
            .takeUntil(viewLifecycleOwner.onDestroyObservable())
            .doOnDispose { Log.d(TAG, "onDispose") }
            .subscribe { t ->
                showErrorSnackBar(view, t)
            }
    }

    private fun showErrorSnackBar(view: View, t: Throwable) {
        Snackbar.make(view, t.message ?: "ERROR", Snackbar.LENGTH_INDEFINITE).apply {
            setAction("RETRY") {
                viewModel.loadMatchStats()
                dismiss()
            }
            show()
        }
    }

}
