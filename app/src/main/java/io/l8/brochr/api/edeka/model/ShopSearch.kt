package io.l8.brochr.api.edeka.model

import io.l8.brochr.api.edeka.Request
import io.l8.brochr.models.ShopSearch

class ShopSearch : ShopSearch {
    override fun getShops(queryString: String, receiver: ShopSearch.ShopQueryResultReceiver) {
        Request.shopQuery(queryString, receiver)
    }
}