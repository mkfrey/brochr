package io.l8.brochr.api.edeka

class Constants {
    companion object {
        const val OFFER_QUERY_URL = "https://www.edeka.de/eh/service/eh/offers?marketId="
        const val OFFER_QUERY_LIMIT_SUFFIX = "&limit=89899"
        const val SHOP_QUERY_RESULT_LIMIT = "10"
        const val SHOP_QUERY_FIELDS = "marktID_tlc,plz_tlc,ort_tlc,strasse_tlc,name_tlc,geoLat_doubleField_d,geoLng_doubleField_d,freigabeVonDatum_longField_l,freigabeBisDatum_longField_l,datumAppHiddenVon_longField_l,datumAppHiddenBis_longField_l,regionName_tlc"
        const val SHOP_QUERY_URL = "https://www.edeka.de/search.xml"
        const val SHOP_IMAGE_URL = "https://www.edeka.de/b2c-design/global/core/assets/organisms/m401-header/logo.png"
        // TODO: Find a more suitable user agent
        const val USER_AGENT = "Brochr Alpha Edition"
    }
}