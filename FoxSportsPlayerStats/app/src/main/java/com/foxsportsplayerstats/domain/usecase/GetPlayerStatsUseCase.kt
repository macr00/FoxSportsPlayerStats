package com.foxsportsplayerstats.domain.usecase

import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.domain.*
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

data class GetPlayerStatsRequest(val teamId: Int, val playerId: Int): UseCaseRequest

class GetPlayerStatsUseCase
@Inject
constructor(
    schedulers: RxSchedulers,
    private val repository: Repository
) : BaseUseCase<GetPlayerStatsRequest, PlayerDetailsModel>(schedulers) {

    private val responseMapper =
        Function<PlayerDetailsModel, UseCaseResponse<PlayerDetailsModel>> { data ->
            UseCaseResponse.Success(data)
        }

    override fun useCaseObservable(request: GetPlayerStatsRequest): Observable<UseCaseResponse<PlayerDetailsModel>> {
        return repository.getPlayerStats(request).map(responseMapper)
    }

    companion object {
        const val TAG = "GetPlayerStatsUseCase"
    }
}