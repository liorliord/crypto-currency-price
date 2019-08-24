package com.dagan.lior.crypto_currency_market_price.bitcoinanimation

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dagan.lior.crypto_currency_market_price.R
import com.dagan.lior.crypto_currency_market_price.login.view.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    companion object {
        const val WELCOME_ACTIVITY_MILLI_DELAY : Long = 2000
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        (bitcoinAnimationImageView.background as AnimationDrawable).start()

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        },
            WELCOME_ACTIVITY_MILLI_DELAY
        )
    }

}
