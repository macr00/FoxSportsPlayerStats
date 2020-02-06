package com.foxsportsplayerstats.domain.player

import com.foxsportsplayerstats.domain.BaseUseCase
import com.foxsportsplayerstats.domain.RxSchedulers
import com.foxsportsplayerstats.domain.UseCaseRequest
import com.foxsportsplayerstats.domain.UseCaseResponse
import com.foxsportsplayerstats.network.FoxSportsApi
import com.foxsportsplayerstats.network.PlayerDetailedStats
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

data class GetPlayerStatsRequest(
    val playerId: String,
    val teamId: String
): UseCaseRequest

class GetPlayerStatsUseCase
@Inject
constructor(
    schedulers: RxSchedulers,
    private val api: FoxSportsApi
) : BaseUseCase<GetPlayerStatsRequest, PlayerStatsData>(schedulers) {

    private val responseMapper =
        Function<PlayerDetailedStats, UseCaseResponse<PlayerStatsData>> { stats ->
            UseCaseResponse.Success(PlayerStatsData(stats))
        }

    override fun useCaseObservable(request: GetPlayerStatsRequest): Observable<UseCaseResponse<PlayerStatsData>> {
        return api.getPlayerDetailedStats(request.teamId, request.playerId)
            .map(responseMapper)
            .onErrorReturn { t: Throwable -> UseCaseResponse.Error(t) }
            .startWith(UseCaseResponse.Loading())
    }
}