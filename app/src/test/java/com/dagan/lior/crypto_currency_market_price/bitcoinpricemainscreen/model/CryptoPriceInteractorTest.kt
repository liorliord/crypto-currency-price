package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model

import org.junit.Test

import org.junit.ClassRule
import java.lang.Exception
import java.sql.Date

class CryptoPriceInteractorTest : CryptoPriceInteractor.OnFinishListener {
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }
    override fun onPriceResultSuccess(price: Double) {
        assert(price >= 0)
    }

    private fun isLegalUnixTimestamp(unixTimestamp: Long): Boolean{
        try {
            Date(((unixTimestamp * 1000)))
            return true
        } catch(e: Exception) {
            return false
        }
    }

    override fun onChartResultSuccess(chartData: MutableList<Pair<Long, Double>>) {
        for ((x, y) in chartData) {
            assert(isLegalUnixTimestamp(x))
            assert(y >= 0)
        }
    }

    override fun onResultFailed(error: String) {
        assert(when(error) {
            "error fetching chart data" -> true
            "error fetching price data" -> true
            else -> false
        })
    }

    @Test
    fun requestCryptoPriceDataFromAPI() {
        CryptoPriceInteractor().requestCryptoPriceDataFromAPI(this)
    }

    @Test
    fun requestCryptoChartDataFromAPI() {
        for (i in -10..20) {
            CryptoPriceInteractor().requestCryptoChartDataFromAPI(this, i)
        }
    }
}