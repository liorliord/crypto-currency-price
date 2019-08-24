package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.view

import android.graphics.Color
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.sql.Date
import java.text.SimpleDateFormat

class CryptoPriceChartAdapter {
    fun setChart(lineChart: LineChart, chartData: MutableList<Pair<Long, Double>>) {
        val entries = ArrayList<Entry>()

        for (pair in chartData) {
            entries.add(Entry(pair.first.toFloat(), pair.second.toFloat()))
        }

        val dataSet = LineDataSet(entries, "price")
        dataSet.color = Color.BLUE
        dataSet.setCircleColor(Color.BLACK)
        val lineData = LineData(dataSet)
        val xAxis = lineChart.xAxis

        xAxis.valueFormatter = MyValueFormatter()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 30F
        lineChart.data = lineData
        lineChart.invalidate()
        lineChart.description.text = "bitcoin market price over time"
    }

    class MyValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return SimpleDateFormat("dd-MM-yy").format(Date(((value * 1000).toLong())))
        }
    }
}
