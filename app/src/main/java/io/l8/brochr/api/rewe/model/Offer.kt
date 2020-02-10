package io.l8.brochr.api.rewe.model

import kotlinx.android.parcel.Parcelize


@Parcelize
class Offer(
    val id: String,
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
            offer: io.l8.brochr.api.rewe.api_model.Offer,
            categoryMap: Map<String, Int>?
        ): Offer {
            val numericalCategoryIds = arrayListOf<Int>()

            categoryMap?.let { map ->
                offer.categoryIDs?.forEach { category ->
                    map[category]?.let { numericalId ->
                        numericalCategoryIds.add(numericalId)
                    }
                }
            }

            return Offer(
                offer.id,
                offer.name,
                offer.price,
                offer.currency,
                offer.discount,
                null,
                offer._links?.imageXl?.href ?: offer._links?.image?.href,
                offer._links?.image?.href + "?resize=200px:200px",
                numericalCategoryIds.toIntArray()
            )
        }
    }
}