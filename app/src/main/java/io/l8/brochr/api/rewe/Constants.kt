package io.l8.brochr.api.rewe

interface Constants {
    companion object {
        const val USER_AGENT = "REWE-Mobile-App/3.4.17 Android/8.1.0"
        const val MARKET_IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4c/Logo_REWE.svg/320px-Logo_REWE.svg.png"
        const val OFFER_QUERY_URL = "https://mobile-api.rewe.de/products/offer-search?marketId="
        const val MARKET_QUERY_URL = "https://mobile-api.rewe.de/mobile/markets/market-search?query="
        const val RESPONSE_CONTENT_TYPE = "application"
        const val SHOP_QUERY_RESPONSE_CONTENT_SUBTYPE = "json"
        const val OFFER_QUERY_RESPONSE_CONTENT_SUBTYPE = "json"
    }
}