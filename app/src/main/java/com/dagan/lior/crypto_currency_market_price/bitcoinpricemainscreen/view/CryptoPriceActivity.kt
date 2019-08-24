package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dagan.lior.crypto_currency_market_price.R
import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model.CryptoPriceInteractor
import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model.FirebaseInteractor
import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.presenter.CryptoPricePresenter
import kotlinx.android.synthetic.main.activity_main.*

class CryptoPriceActivity
    : AppCompatActivity(), CryptoPriceView {

    private lateinit var cryptoPricePresenter : CryptoPricePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        priceProgressBar.visibility = View.GONE
        cryptoPricePresenter = CryptoPricePresenter(this, CryptoPriceInteractor(), FirebaseInteractor(intent.getStringExtra("currentUser")))

        cryptoPricePresenter.getPreviousPrice()

        refreshButton.setOnClickListener {
            cryptoPricePresenter.getPriceData()
            cryptoPricePresenter.getChartData()
        }

        timespanSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                cryptoPricePresenter.getChartData()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        cryptoPricePresenter.getPriceData()
        cryptoPricePresenter.getChartData()
    }

    override fun onStop() {
        cryptoPricePresenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        cryptoPricePresenter.onDestroy()
        super.onDestroy()
    }
    @SuppressLint("SetTextI18n")
    override fun setPrice(price: Double) {
        priceTextView.text = "$ $price"
    }

    override fun setChart(chartData: MutableList<Pair<Long, Double>>) {
        CryptoPriceChartAdapter().setChart(bitcoinPriceChart, chartData)
    }

    override fun setPreviousPrice(price: String) {
        previousPriceViewed.text = price
    }

    override fun showChartProgress() {
        chartProgressBar.visibility = View.VISIBLE
    }

    override fun hideChartProgress() {
        chartProgressBar.visibility = View.GONE
    }

    override fun showPriceProgress() {
        priceProgressBar.visibility = View.VISIBLE
    }

    override fun hidePriceProgress() {
        priceProgressBar.visibility = View.GONE
    }

    override fun showPreviousPriceProgress() {
        previousPriceProgress.visibility = View.VISIBLE
    }

    override fun hidePreviousPriceProgress() {
        previousPriceProgress.visibility = View.GONE
    }

    override fun getSeekBarProgress(): Int {
        return timespanSeekBar.progress
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayTimespan(timespan: String) {
        timespanTextView.text = timespan
    }

}
