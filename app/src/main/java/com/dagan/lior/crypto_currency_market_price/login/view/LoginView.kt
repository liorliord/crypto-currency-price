package com.dagan.lior.crypto_currency_market_price.login.view

interface LoginView {
    fun showToast(message: String, length: Int)
    fun endActivity()
    fun clearPassword()
    fun showLoginProgress()
    fun hideLoginProgress()
}