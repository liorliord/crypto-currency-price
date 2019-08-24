package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CryptoPriceInteractor {
    private var disposable: Disposable? = null

    private val apiCryptoPrice by lazy {
        ApiCryptoPrice.create()
    }

    private val apiCryptoChart by lazy {
        ApiCryptoChart.create()
    }

    fun requestCryptoPriceDataFromAPI(onFinishListener: OnFinishListener) {
        disposable = apiCryptoPrice.getPrice()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onFinishListener.onPriceResultSuccess(result.market_price_usd)},
                { onFinishListener.onResultFailed("error fetching price data")}
            )
    }

    fun requestCryptoChartDataFromAPI(onFinishListener: OnFinishListener, numberOfWeeks: Int) {
        disposable = apiCryptoChart.getChart(ApiCryptoChart.numberOfWeeksToTimespan(numberOfWeeks))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    result -> var list: MutableList<Pair<Long, Double>> = mutableListOf()
                    result.values.forEach {  pair -> list.add(pair.x to pair.y)}
                    onFinishListener.onChartResultSuccess(list)
                },
                { onFinishListener.onResultFailed("error fetching chart data")}
            )
    }

    interface OnFinishListener {
        fun onPriceResultSuccess(price: Double)
        fun onChartResultSuccess(chartData: MutableList<Pair<Long, Double>>)
        fun onResultFailed(error: String)
    }
}