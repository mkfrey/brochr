package io.l8.brochr.api.edeka.model

import kotlinx.android.parcel.Parcelize

@Parcelize
class Offer(
    val id: Int,
    override val name: String,
    override val price: Float,
    override val priceUnit: String,
    override val discountOrRegularPrice: String?,
    override val description: String?,
    override val imageUrl: String?,
    override val thumbnailUrl: String?,
    override val categoryIndices: IntArray?
) : io.l8.brochr.models.Offer {
    companion object {
        fun from(
            offer: io.l8.brochr.api.edeka.api_model.Offer,
            categoryMap: Map<Int, Int>? = null
        ): Offer {
            val numericalCategoryIds = arrayListOf<Int>()

            categoryMap?.get(offer.warengruppeid)?.let {
                numericalCategoryIds.add(it)
            }

            return Offer(
                offer.angebotid,
                offer.titel,
                offer.preis,
                "€",
                null,
                offer.beschreibung,
                offer.bild_app,
                offer.bild_web130,
                numericalCategoryIds.toIntArray()
            )
        }
    }
}