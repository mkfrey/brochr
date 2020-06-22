package io.l8.brochr.models

import android.os.Parcelable

interface Shop: Parcelable {
    val name: String
    val street: String
    val postalCode: String
    val city: String
    val imageUrl: String?

    fun equals(shop: Shop): Boolean

    fun fetchOffers(receiver: OfferQueryResultReceiver)
    fun hasCategories(): Boolean {
        return false
    }

    interface OfferQueryResultReceiver {
        fun onQueryResult(result: OfferQueryResult)
    }
}

sealed class OfferQueryResult {
    class Categorized(val offers: ArrayList<Offer>, val categories: Array<String>): OfferQueryResult()
    class NonCategorized(val offers: ArrayList<Offer>): OfferQueryResult()
    object None: OfferQueryResult()
    data class Error(val errorMessage: String?): OfferQueryResult()
}