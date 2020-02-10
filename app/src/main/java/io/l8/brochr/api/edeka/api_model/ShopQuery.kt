package io.l8.brochr.api.edeka.api_model

import kotlinx.serialization.Serializable

@Serializable
class Shop(
    /**
     * Internal numerical ID of the market as string
     * @example "14685"
     */
    val marktID_tlc: String,
    /**
     * Friendly name of the market
     * @example "Edeka Mustermann"
     */

    val name_tlc: String,
    /**
     * Street name and house number
     * @example "Musterstr. 12"
     */
    val strasse_tlc: String,
    /**
     * City
     * @example "Musterstadt"
     */
    val ort_tlc: String,

    /**
     * Postal code as string
     * @example "87654"
     */
    val plz_tlc: String
)

@Serializable
class ShopQueryResponse(
    /**
     * Array of the shops found
     */
    val docs: ArrayList<Shop>
)

@Serializable
class ShopQueryResult(
    /**
     * Response object
     */
    val response: ShopQueryResponse
)

