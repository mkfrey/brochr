package io.l8.brochr.models

interface ShopSearch {
    fun getShops(queryString: String, receiver: ShopQueryResultReceiver)

    interface ShopQueryResultReceiver {
        fun onQueryResult(result: ArrayList<Shop>?)
    }
}