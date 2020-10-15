package io.l8.brochr.api.edeka.deserialize

import io.l8.brochr.api.edeka.Request
import io.l8.brochr.api.edeka.api_model.ShopQueryResult
import io.l8.brochr.models.Shop
import io.l8.brochr.models.ShopSearch
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody

class ShopQueryDeserializer(val resultReceiver: ShopSearch.ShopQueryResultReceiver) :
    Request.OnRequestResponse {
    override fun onResponse(responseBody: ResponseBody?) {
        responseBody?.string()?.let { bodyString ->
            val deserializationResult =
                Json{ ignoreUnknownKeys = true }.decodeFromString(ShopQueryResult.serializer(), bodyString)

            val shopList = deserializationResult.response.docs.map { shop ->
                io.l8.brochr.api.edeka.model.Shop.from(shop)
            }

            val shopArrayList = ArrayList<Shop>(shopList)

            resultReceiver.onQueryResult(shopArrayList)

        } ?: run {
            resultReceiver.onQueryResult(null)
        }
        responseBody?.close()
    }
}
