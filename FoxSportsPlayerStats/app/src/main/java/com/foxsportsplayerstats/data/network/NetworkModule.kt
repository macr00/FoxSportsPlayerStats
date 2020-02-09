package com.foxsportsplayerstats.data.network

import com.foxsportsplayerstats.app.di.Config
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideHttpClient(config: Config): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    if (config.isDebug()) level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    fun provideFoxSportsApi(client: OkHttpClient): FoxSportsApi {
        return Retrofit.Builder()
            .baseUrl(FoxSportsApi.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(FoxSportsApi::class.java)
    }
}