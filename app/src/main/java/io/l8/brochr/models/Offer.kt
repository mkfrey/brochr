package io.l8.brochr.models

import android.os.Parcelable

interface Offer: Parcelable {
    val name: String
    val description: String?
    val priceUnit: String
    val price: Float
    val discountOrRegularPrice: String?
    val thumbnailUrl: String?
    val imageUrl: String?
    val categoryIndices: IntArray?
}