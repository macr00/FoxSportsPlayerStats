package com.foxsportsplayerstats.domain.usecase

import android.util.Log
import com.foxsportsplayerstats.domain.*
import com.foxsportsplayerstats.domain.model.MatchStatModel
import com.foxsportsplayerstats.network.MatchStatData
import com.foxsportsplayerstats.network.FoxSportsApi
import com.foxsportsplayerstats.network.toModel
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

object GetMatchStatsRequest : UseCaseRequest

class GetMatchStatsUseCase
@Inject
constructor(
    schedulers: RxSchedulers,
    private val api: FoxSportsApi
) : BaseUseCase<GetMatchStatsRequest, List<MatchStatModel>>(schedulers) {

    private val responseMapper =
        Function<List<MatchStatData>, UseCaseResponse<List<MatchStatModel>>> { list ->
            UseCaseResponse.Success(list.map { it.toModel() })
        }

    override fun useCaseObservable(
        request: GetMatchStatsRequest
    ): Observable<UseCaseResponse<List<MatchStatModel>>> {
        return api.getMatchStats()
            .map(responseMapper)
    }

    companion object {
        const val TAG = "GetMatchStatsUseCase"
    }
}