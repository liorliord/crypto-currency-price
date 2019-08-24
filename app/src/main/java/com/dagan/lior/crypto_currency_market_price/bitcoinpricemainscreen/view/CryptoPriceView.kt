package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.view

interface CryptoPriceView {
    fun setPrice(price: Double)
    fun setChart(chartData: MutableList<Pair<Long, Double>>)
    fun setPreviousPrice(price: String)
    fun showChartProgress()
    fun hideChartProgress()
    fun showPriceProgress()
    fun hidePriceProgress()
    fun showPreviousPriceProgress()
    fun hidePreviousPriceProgress()
    fun getSeekBarProgress(): Int
    fun displayError(error: String)
    fun displayTimespan(timespan: String)
}