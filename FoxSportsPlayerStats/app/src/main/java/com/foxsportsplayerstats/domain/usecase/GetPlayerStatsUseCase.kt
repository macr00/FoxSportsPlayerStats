package com.foxsportsplayerstats.domain.usecase

import com.foxsportsplayerstats.domain.BaseUseCase
import com.foxsportsplayerstats.domain.RxSchedulers
import com.foxsportsplayerstats.domain.UseCaseRequest
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.model.PlayerDetailsModel
import com.foxsportsplayerstats.network.FoxSportsApi
import com.foxsportsplayerstats.network.PlayerDetailsData
import com.foxsportsplayerstats.network.toModel
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

data class GetPlayerStatsRequest(
    val teamId: Int,
    val playerId: Int
): UseCaseRequest

class GetPlayerStatsUseCase
@Inject
constructor(
    schedulers: RxSchedulers,
    private val api: FoxSportsApi
) : BaseUseCase<GetPlayerStatsRequest, PlayerDetailsModel>(schedulers) {

    private val responseMapper =
        Function<PlayerDetailsData, UseCaseResponse<PlayerDetailsModel>> { data ->
            UseCaseResponse.Success(data.toModel())
        }

    override fun useCaseObservable(request: GetPlayerStatsRequest): Observable<UseCaseResponse<PlayerDetailsModel>> {
        return api.getPlayerDetailedStats(request.teamId.toString(), request.playerId.toString())
            .map(responseMapper)
    }

    companion object {
        const val TAG = "GetPlayerStatsUseCase"
    }
}