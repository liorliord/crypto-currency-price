package com.dagan.lior.crypto_currency_market_price.login.model

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseInteractor(context: Context) {

    init {
        FirebaseApp.initializeApp(context)
    }

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun processEmailAndPasswordInput(onFinishListener: OnFinishListener, email: String, password: String) {
        firebaseAuth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.signInMethods!!.isNotEmpty()) {
                        tryToSignIn(onFinishListener, email, password)
                    } else {
                        tryToSignUp(onFinishListener, email, password)
                    }
                } else {
                    task.exception?.message?.let { message -> onFinishListener.onSignInFailed(message) }
                }
            }
    }

    private fun tryToSignUp(onFinishListener: OnFinishListener, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                onFinishListener.onSignInSuccess(firebaseAuth.currentUser)
            } else {
                task.exception?.message?.let { message -> onFinishListener.onSignInFailed(message) }
            }
        }
    }

    private fun tryToSignIn(onFinishListener: OnFinishListener, email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onFinishListener.onSignInSuccess(firebaseAuth.currentUser)
                } else {
                    task.exception?.message?.let { message -> onFinishListener.onSignInFailed(message) }
                }
            }
    }

    interface OnFinishListener {
        fun onSignInSuccess(currentUser: FirebaseUser?)
        fun onSignInFailed(error: String)
    }
}