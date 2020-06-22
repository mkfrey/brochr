package io.l8.brochr.api.edeka.model

import io.l8.brochr.api.edeka.Constants
import io.l8.brochr.api.edeka.Request
import kotlinx.android.parcel.Parcelize

@Parcelize
class Shop(
    val id: String,
    override val name: String,
    override val street: String,
    override val postalCode: String,
    override val city: String,
    override val imageUrl: String?
) : io.l8.brochr.models.Shop {
    companion object {
        fun from(shop: io.l8.brochr.api.edeka.api_model.Shop): Shop {
            return Shop(
                shop.marktID_tlc,
                shop.name_tlc,
                shop.strasse_tlc,
                shop.plz_tlc,
                shop.ort_tlc,
                Constants.SHOP_IMAGE_URL
            )
        }
    }

    override fun equals(shop: io.l8.brochr.models.Shop): Boolean {
        return name === shop.name && street === shop.street && postalCode === shop.postalCode
                && city === shop.city
    }

    override fun fetchOffers(receiver: io.l8.brochr.models.Shop.OfferQueryResultReceiver) {
        Request.offerQuery(id, receiver)
    }

    override fun hasCategories(): Boolean {
        return true
    }
}