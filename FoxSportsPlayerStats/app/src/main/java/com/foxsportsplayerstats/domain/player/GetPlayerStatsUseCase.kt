package com.foxsportsplayerstats.domain.player

import com.foxsportsplayerstats.domain.BaseUseCase
import com.foxsportsplayerstats.domain.RxSchedulers
import com.foxsportsplayerstats.domain.UseCaseRequest
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.domain.model.PlayerStatsModel
import com.foxsportsplayerstats.network.FoxSportsApi
import com.foxsportsplayerstats.network.PlayerDetailedStats
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
) : BaseUseCase<GetPlayerStatsRequest, PlayerStatsModel>(schedulers) {

    private val responseMapper =
        Function<PlayerDetailedStats, UseCaseResponse<PlayerStatsModel>> { stats ->
            UseCaseResponse.Success(
                PlayerStatsModel(
                    stats
                )
            )
        }

    override fun useCaseObservable(request: GetPlayerStatsRequest): Observable<UseCaseResponse<PlayerStatsModel>> {
        return api.getPlayerDetailedStats(request.teamId.toString(), request.playerId.toString())
            .map(responseMapper)
            .onErrorReturn { t: Throwable -> UseCaseResponse.Error(t) }
            .startWith(UseCaseResponse.Loading())
    }
}