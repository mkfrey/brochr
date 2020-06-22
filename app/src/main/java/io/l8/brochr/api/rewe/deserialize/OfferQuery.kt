package io.l8.brochr.api.rewe.deserialize

import io.l8.brochr.api.rewe.Constants
import io.l8.brochr.api.rewe.Request
import io.l8.brochr.models.Offer
import io.l8.brochr.models.OfferQueryResult
import io.l8.brochr.models.Shop
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

class OfferQueryDeserializer(val resultReceiver: Shop.OfferQueryResultReceiver) :
    Request.OnRequestResponse {
    override fun onResponse(responseBody: ResponseBody?) {
        responseBody?.let { body ->
            body.contentType()?.let { contentType ->
                // Check the ContentType, CloudFlare might answer instead of the API
                println(contentType.toString())
                if (contentType.type == Constants.RESPONSE_CONTENT_TYPE && contentType.subtype == Constants.OFFER_QUERY_RESPONSE_CONTENT_SUBTYPE) {
                    val deserializationResult = Json.nonstrict.parse(
                        io.l8.brochr.api.rewe.api_model.OfferQueryResult.serializer(),
                        body.string()
                    )
                    body.close()

                    val categories = deserializationResult?._meta?.categories
                    val categoryIdMap = mutableMapOf<String, Int>()
                    val categoryNames = mutableListOf<String>()

                    categories?.forEachIndexed { index, offerCategory ->
                        categoryNames.add(index, offerCategory.name)
                        categoryIdMap[offerCategory.id] = index
                    }

                    val offerList: List<Offer> = deserializationResult.items.map {
                        io.l8.brochr.api.rewe.model.Offer.from(it, categoryIdMap)
                    }

                    val offerArray = ArrayList(offerList)

                    resultReceiver.onQueryResult(
                        OfferQueryResult.Categorized(
                            offerArray,
                            categoryNames.toTypedArray()
                        )
                    )
                    return
                }
            }
            body.close()
        }

        resultReceiver.onQueryResult(OfferQueryResult.None)
    }
}