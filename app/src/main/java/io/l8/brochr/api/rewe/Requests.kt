package io.l8.brochr.api.rewe

import io.l8.brochr.api.rewe.deserialize.OfferQueryDeserializer
import io.l8.brochr.api.rewe.deserialize.ShopQueryDeserializer
import io.l8.brochr.models.Shop
import io.l8.brochr.models.ShopSearch
import okhttp3.*
import java.io.IOException


interface Request {
    companion object {
        private val client = OkHttpClient()

        fun buildGetRequest(url: String): okhttp3.Request {
            return okhttp3.Request.Builder()
                .addHeader("User-Agent", Constants.USER_AGENT)
                .addHeader("Accept", "application/json")
                .url(url).build()
        }

        private fun genericFetchUrl(url: String, receiver: OnRequestResponse) {
            client.newCall(buildGetRequest(url)).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    receiver.onResponse(response.body)
                }

                override fun onFailure(call: Call, e: IOException) {
                    receiver.onResponse(null)
                }
            })
        }

        fun shopQuery(query: String, receiver: ShopSearch.ShopQueryResultReceiver) {
            genericFetchUrl(
                UrlBuilder.buildMarketQueryUrl(query),
                ShopQueryDeserializer(receiver)
            )
        }

        fun offerQuery(marketId: String, receiver: Shop.OfferQueryResultReceiver) {
            genericFetchUrl(
                UrlBuilder.buildOfferQueryUrl(marketId),
                OfferQueryDeserializer(
                    receiver
                )
            )
        }
    }

    interface OnRequestResponse {
        fun onResponse(responseBody: ResponseBody?)
    }

}