package io.l8.brochr.api.rewe

import java.net.URLEncoder

interface UrlBuilder {
    companion object {
        fun buildOfferQueryUrl(marketId: String): String {
            return "${Constants.OFFER_QUERY_URL}${marketId}"
        }

        fun buildMarketQueryUrl(query: String): String {
            return "${Constants.MARKET_QUERY_URL}${URLEncoder.encode(query, "utf-8")}"
        }
    }
}