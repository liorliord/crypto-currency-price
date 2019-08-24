package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.presenter

import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model.CryptoPriceInteractor
import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model.FirebaseInteractor
import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.view.CryptoPriceView

class CryptoPricePresenter(private var cryptoPriceView: CryptoPriceView?,
                           private val cryptoPriceInteracter: CryptoPriceInteractor,
                           private val firebaseInteractor: FirebaseInteractor)
    : CryptoPriceInteractor.OnFinishListener,
    FirebaseInteractor.OnFinishListener{

    private var lastPrice: Double? = null

    fun getPriceData() {
        cryptoPriceView?.setPrice(0.0)
        cryptoPriceView?.showPriceProgress()
        cryptoPriceInteracter.requestCryptoPriceDataFromAPI(this)
    }

    fun getChartData() {
        cryptoPriceView?.showChartProgress()
        val numWeeks = cryptoPriceView?.getSeekBarProgress()
        cryptoPriceView?.displayTimespan(when (numWeeks) {
            0,10 -> "All Time"
            else -> numWeeks.toString() + " week overview"
        })
        cryptoPriceInteracter.requestCryptoChartDataFromAPI(this, numWeeks!!)
    }

    fun getPreviousPrice() {
        cryptoPriceView?.setPreviousPrice("")
        cryptoPriceView?.showChartProgress()
        firebaseInteractor.requestPreviousPriceViewed(this)
    }

    override fun onChartResultSuccess(chartData: MutableList<Pair<Long, Double>>) {
        cryptoPriceView?.hideChartProgress()
        cryptoPriceView?.setChart(chartData)
    }

    override fun onPriceResultSuccess(price: Double) {
        cryptoPriceView?.hidePriceProgress()
        lastPrice = price
        cryptoPriceView?.setPrice(price)
    }

    override fun onResultFailed(error: String) {
        cryptoPriceView?.hidePriceProgress()
        cryptoPriceView?.hideChartProgress()
        cryptoPriceView?.displayError(error)
    }

    override fun onPreviousPriceRequestSuccess(price: String) {
        cryptoPriceView?.hidePreviousPriceProgress()
        cryptoPriceView?.setPreviousPrice(price)
    }

    override fun onPreviousPriceRequestFailed(error: String) {
        cryptoPriceView?.hidePreviousPriceProgress()
        cryptoPriceView?.setPreviousPrice("no previous information")
    }

    fun onStop() {
        lastPrice?.let { firebaseInteractor.setPreviousPrice(it) }
    }

    fun onDestroy() {
        cryptoPriceView = null
    }
}