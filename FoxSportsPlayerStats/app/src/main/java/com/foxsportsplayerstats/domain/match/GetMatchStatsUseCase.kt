package com.foxsportsplayerstats.domain.match

import com.foxsportsplayerstats.domain.*
import com.foxsportsplayerstats.network.MatchStat
import com.foxsportsplayerstats.network.MatchStatsApi
import io.reactivex.Observable
import io.reactivex.functions.Function

object GetMatchStatsRequest : UseCaseRequest

class GetMatchStatsUseCase
constructor(
    schedulers: RxSchedulers,
    private val api: MatchStatsApi
) : BaseUseCase<GetMatchStatsRequest, MatchStatsData>(schedulers) {

    private val responseMapper =
        Function<List<MatchStat>, UseCaseResponse<MatchStatsData>> { list ->
            UseCaseResponse.Success(MatchStatsData(list))
        }

    override fun useCaseObservable(upstream: Observable<GetMatchStatsRequest>): Observable<UseCaseResponse<MatchStatsData>> {
        return upstream.switchMap { api.getMatchStats() }
            .map(responseMapper)
            .onErrorReturn { t: Throwable -> UseCaseResponse.Error(t) }
            .startWith(UseCaseResponse.Loading())
    }
}