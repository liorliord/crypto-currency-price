package com.dagan.lior.crypto_currency_market_price.bitcoinpricemainscreen.model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseInteractor(private val currentUser: String) {

    private val firebaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    fun requestPreviousPriceViewed(onFinishListener: OnFinishListener) {
        firebaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(currentUser)) {
                    onFinishListener.onPreviousPriceRequestSuccess(snapshot.child(currentUser).value.toString())
                } else {
                    onFinishListener.onPreviousPriceRequestFailed("no data found")
                }
            }
        })
    }

    fun setPreviousPrice(price: Double) {
        firebaseReference.child(currentUser).setValue(price)
    }

    interface OnFinishListener {
        fun onPreviousPriceRequestSuccess(price: String)
        fun onPreviousPriceRequestFailed(error: String)
    }
}