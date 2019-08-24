package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCryptoChart {
    @GET("charts/market-price?&format=json")
    fun getChart(@Query("timespan") timespan: String): Observable<ChartModel.Result>

    companion object {
        const val BASE_URL : String = "https://api.blockchain.info/"
        const val MIN_WEEK_PROGRESS_VAL: Int = 1
        const val MAX_WEEK_PROGRESS_VAL: Int = 9

        fun numberOfWeeksToTimespan(numberOfWeeks: Int): String {
            return when(numberOfWeeks) {
                in MIN_WEEK_PROGRESS_VAL..(MAX_WEEK_PROGRESS_VAL) -> (numberOfWeeks * 7).toString() + "days"
                else -> "all"
            }
        }

        fun create(): ApiCryptoChart {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiCryptoChart::class.java)
        }
    }
}
