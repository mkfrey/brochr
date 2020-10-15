package io.l8.brochr.api.edeka.deserialize

import io.l8.brochr.api.edeka.Request
import io.l8.brochr.models.Offer
import io.l8.brochr.models.OfferQueryResult
import io.l8.brochr.models.Shop
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

class OfferQueryDeserializer(val resultReceiver: Shop.OfferQueryResultReceiver) :
    Request.OnRequestResponse {
    override fun onResponse(responseBody: ResponseBody?) {
        responseBody?.string()?.let { body ->
            // TODO: Add JSON check to avoid crash on invalid payload such as a captcha => Check if content type is JSON
            val deserializationResult = Json{ ignoreUnknownKeys = true }.decodeFromString(
                io.l8.brochr.api.edeka.api_model.OfferQueryResult.serializer(),
                body
            )
            val deserializedOffers = deserializationResult.docs
            val categoryIdMap = mutableMapOf<Int, Int>()
            val categoryNames = mutableListOf<String>()

            deserializedOffers.filter { it.warengruppeid != null && it.warengruppe != null }
                .distinctBy { it.warengruppeid }.sortedBy { it.warengruppeid }
                .forEachIndexed { index, offer ->
                    categoryNames.add(index, offer.warengruppe!!)
                    categoryIdMap[offer.warengruppeid!!] = index
                }

            val offerList: List<Offer> = deserializationResult.docs.map {
                io.l8.brochr.api.edeka.model.Offer.from(it, categoryIdMap)
            }

            val shopArray = ArrayList(offerList)

            resultReceiver.onQueryResult(OfferQueryResult.Categorized(shopArray, categoryNames.toTypedArray()))
        } ?: run {
            resultReceiver.onQueryResult(OfferQueryResult.None)
        }
        responseBody?.close()
    }
}