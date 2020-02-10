package io.l8.brochr.api.rewe.deserialize

import io.l8.brochr.api.rewe.Constants
import io.l8.brochr.api.rewe.Request
import io.l8.brochr.api.rewe.api_model.ShopQueryResult
import io.l8.brochr.models.Shop
import io.l8.brochr.models.ShopSearch
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

class ShopQueryDeserializer(val resultReceiver: ShopSearch.ShopQueryResultReceiver) :
    Request.OnRequestResponse {
    override fun onResponse(responseBody: ResponseBody?) {
        responseBody?.let { body ->
            body.contentType()?.let { contentType ->
                // Check the ContentType, CloudFlare might answer instead of the API
                if (contentType.type == Constants.RESPONSE_CONTENT_TYPE && contentType.subtype == Constants.SHOP_QUERY_RESPONSE_CONTENT_SUBTYPE) {
                    val deserializationResult =
                        Json.nonstrict.parse(ShopQueryResult.serializer(), body.string())

                    body.close()

                    val shopList = deserializationResult.items.map { shop ->
                        io.l8.brochr.api.rewe.model.Shop.from(shop)
                    }

                    val shopArrayList = ArrayList<Shop>(shopList)

                    resultReceiver.onQueryResult(shopArrayList)
                    return
                }
            }
            body.close()
        }
        resultReceiver.onQueryResult(null)
    }
}
