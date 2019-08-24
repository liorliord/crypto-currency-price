package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model

object ChartModel {
    data class Result(val values: MutableList<DataPair>)
    data class DataPair(val x: Long, val y: Double)
}