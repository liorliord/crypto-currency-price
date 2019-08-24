package com.dagan.lior.crypto_currency_market_price.login.model

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseInteractor(context: Context) {

    companion object {
        var currentUser: String? = null
    }

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
                        currentUser = firebaseAuth.currentUser.toString()
                        signIn(onFinishListener, email, password)
                    } else {
                        signUp(onFinishListener, email, password)
                    }
                } else {
                    task.exception?.message?.let { message -> onFinishListener.onSignUpFailed(message) }
                }
            }
    }

    private fun signUp(onFinishListener: OnFinishListener, email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                onFinishListener.onSignUpSuccess(firebaseAuth.currentUser)
                currentUser = firebaseAuth.currentUser.toString()
            } else {
                task.exception?.message?.let { message -> onFinishListener.onSignUpFailed(message) }
            }
        }
    }

    private fun signIn(onFinishListener: OnFinishListener, email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onFinishListener.onSignUpSuccess(firebaseAuth.currentUser)
                } else {
                    task.exception?.message?.let { message -> onFinishListener.onSignUpFailed(message) }
                }
            }
    }

    interface OnFinishListener {
        fun onSignUpSuccess(currentUser: FirebaseUser?)
        fun onSignUpFailed(error: String)
    }
}