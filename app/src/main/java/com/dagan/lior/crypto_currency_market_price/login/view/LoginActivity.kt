package com.dagan.lior.crypto_currency_market_price.login.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dagan.lior.crypto_currency_market_price.R
import com.dagan.lior.crypto_currency_market_price.login.model.FirebaseInteractor
import com.dagan.lior.crypto_currency_market_price.login.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),
    LoginView {
    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPresenter = LoginPresenter(this, this, FirebaseInteractor(this))
        hideLoginProgress()

        signInButton.setOnClickListener {
            loginPresenter.signInButtonClicked(emailEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    override fun showToast(message: String, length: Int) {
        Toast.makeText(this, message, length).show()
    }

    override fun endActivity() {
        this.finish()
    }

    override fun clearPassword() {
        passwordEditText.text.clear()
    }

    override fun showLoginProgress() {
        loginProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoginProgress() {
        loginProgressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.onDestroy()
    }

}
