package io.l8.brochr.api.rewe.model

import io.l8.brochr.api.rewe.Request
import io.l8.brochr.models.ShopSearch

class ShopSearch : ShopSearch {
    override fun getShops(queryString: String, receiver: ShopSearch.ShopQueryResultReceiver) {
        Request.shopQuery(queryString, receiver)
    }
}