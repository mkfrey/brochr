package io.l8.brochr.api.edeka

import io.l8.brochr.api.edeka.deserialize.OfferQueryDeserializer
import io.l8.brochr.api.edeka.deserialize.ShopQueryDeserializer
import io.l8.brochr.models.Shop
import io.l8.brochr.models.ShopSearch
import okhttp3.*
import java.io.IOException


interface Request {
    companion object {
        private val client = OkHttpClient()

        fun prebuildRequest(url: String): okhttp3.Request.Builder {
            return okhttp3.Request.Builder()
                .addHeader("User-Agent", Constants.USER_AGENT)
                .addHeader("Accept", "application/json")
                .url(url)
        }

        fun buildShopQueryRequest(query: String): okhttp3.Request {
            val formBody = FormBody.Builder()
                .add("indent", "off")
                .add("hl", "false")
                .add("rows", Constants.SHOP_QUERY_RESULT_LIMIT)
                .add("q", QueryBuilder.build(query))
                .add("fl", Constants.SHOP_QUERY_FIELDS)
                .build()

            return prebuildRequest(UrlBuilder.buildMarketQueryUrl())
                .method("POST", formBody).build()
        }

        fun buildOfferQueryRequest(marketId: String): okhttp3.Request {
            return prebuildRequest(UrlBuilder.buildOfferQueryUrl(marketId)).build()
        }

        private fun genericFetchUrl(request: okhttp3.Request, receiver: OnRequestResponse) {
            client.newCall(request).enqueue(object : Callback {
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
                buildShopQueryRequest(query),
                ShopQueryDeserializer(receiver)
            )
        }

        fun offerQuery(marketId: String, receiver: Shop.OfferQueryResultReceiver) {
            genericFetchUrl(
                buildOfferQueryRequest(marketId),
                OfferQueryDeserializer(receiver)
            )
        }
    }

    interface OnRequestResponse {
        fun onResponse(responseBody: ResponseBody?)
    }

}