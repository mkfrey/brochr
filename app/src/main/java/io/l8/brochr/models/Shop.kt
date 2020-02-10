package io.l8.brochr.models

import android.os.Parcelable

interface Shop: Parcelable {
    val name: String
    val street: String
    val postalCode: String
    val city: String
    val imageUrl: String?

    fun fetchOffers(receiver: OfferQueryResultReceiver)
    fun hasCategories(): Boolean {
        return false
    }

    interface OfferQueryResultReceiver {
        fun onQueryResult(result: OfferQueryResult)
    }
}

class OfferQueryResult(val offers: ArrayList<Offer>?, val categories: Array<String>? = null)
