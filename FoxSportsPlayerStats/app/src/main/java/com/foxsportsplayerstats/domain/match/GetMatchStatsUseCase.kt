package com.foxsportsplayerstats.domain.match

import android.util.Log
import com.foxsportsplayerstats.domain.*
import com.foxsportsplayerstats.network.MatchStat
import com.foxsportsplayerstats.network.FoxSportsApi
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

object GetMatchStatsRequest : UseCaseRequest

class GetMatchStatsUseCase
@Inject
constructor(
    schedulers: RxSchedulers,
    private val api: FoxSportsApi
) : BaseUseCase<GetMatchStatsRequest, MatchStatsData>(schedulers) {

    private val responseMapper =
        Function<List<MatchStat>, UseCaseResponse<MatchStatsData>> { list ->
            UseCaseResponse.Success(MatchStatsData(list))
        }

    override fun useCaseObservable(request: GetMatchStatsRequest): Observable<UseCaseResponse<MatchStatsData>> {
        return api.getMatchStats()
            .map(responseMapper)
            .onErrorReturn { t: Throwable -> UseCaseResponse.Error(t) }
            .startWith(UseCaseResponse.Loading())
            .doOnEach { Log.d(TAG, it.toString()) }
    }

    companion object {
        const val TAG = "GetMatchStatsUseCase"
    }
}