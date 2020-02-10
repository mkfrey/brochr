package io.l8.brochr.api.edeka

interface UrlBuilder {
    companion object {
        fun buildOfferQueryUrl(marketId: String): String {
            return "${Constants.OFFER_QUERY_URL}${marketId}${Constants.OFFER_QUERY_LIMIT_SUFFIX}"
        }

        fun buildMarketQueryUrl(): String {
            return Constants.SHOP_QUERY_URL
        }
    }
}