package com.foxsportsplayerstats.domain.usecase

import com.foxsportsplayerstats.domain.*
import com.foxsportsplayerstats.domain.model.MatchStatModel
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

object GetMatchStatsRequest : UseCaseRequest

class GetMatchStatsUseCase
@Inject
constructor(
    schedulers: RxSchedulers,
    private val repository: Repository
) : BaseUseCase<GetMatchStatsRequest, List<MatchStatModel>>(schedulers) {

    private val responseMapper =
        Function<List<MatchStatModel>, UseCaseResponse<List<MatchStatModel>>> { data ->
            UseCaseResponse.Success(data)
        }

    override fun useCaseObservable(request: GetMatchStatsRequest): Observable<UseCaseResponse<List<MatchStatModel>>> {
        return repository.getMatchStats().map(responseMapper)
    }

    companion object {
        const val TAG = "GetMatchStatsUseCase"
    }
}