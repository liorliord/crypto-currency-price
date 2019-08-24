package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiCryptoPrice {
    @GET("stats")
    fun getPrice(): Observable<PriceModel.Result>

    companion object {
        const val BASE_URL : String = "https://api.blockchain.info/"
        fun create(): ApiCryptoPrice {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiCryptoPrice::class.java)
        }
    }
}