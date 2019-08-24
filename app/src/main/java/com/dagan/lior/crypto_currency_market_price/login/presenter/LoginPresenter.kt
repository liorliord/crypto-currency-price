package com.dagan.lior.crypto_currency_market_price.login.presenter

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.view.CryptoPriceActivity
import com.dagan.lior.crypto_currency_market_price.login.model.FirebaseInteractor
import com.dagan.lior.crypto_currency_market_price.login.view.LoginView


class LoginPresenter(
    private var loginView: LoginView?,
    private val context: Context,
    private val firebaseInteractor: FirebaseInteractor
) : FirebaseInteractor.OnFinishListener {

    fun signInButtonClicked(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            loginView?.showToast("Please fill in both e-mail & password", Toast.LENGTH_SHORT)
        } else {
            loginView?.clearPassword()
            loginView?.showLoginProgress()
            firebaseInteractor.processEmailAndPasswordInput(this, email, password)
        }
    }

    override fun onSignUpSuccess(currentUser: FirebaseUser?) {
        loginView?.hideLoginProgress()
        val intent = Intent(context, CryptoPriceActivity::class.java)
        intent.putExtra("currentUser", currentUser?.uid.toString())
        context.startActivity(intent)
        loginView?.endActivity()
    }

    override fun onSignUpFailed(error: String) {
        loginView?.hideLoginProgress()
        loginView?.showToast(error, Toast.LENGTH_SHORT)
    }

    fun onDestroy() {
        loginView = null
    }
}

