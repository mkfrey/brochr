package io.l8.brochr.api.rewe.api_model

import kotlinx.serialization.Serializable

@Serializable
class ShopQueryResult(
    /**
     * Array of all found shops
     */
    val items: ArrayList<Shop>
)

@Serializable
class Shop(
    /**
     * Internal numerical ID of the shop as string
     * @example "466549"
     */
    val id: String,
    /**
     * Name of the shop, presumably the business name
     * @example "REWE Musterstadt oHG"
     */
    val name: String,
    /**
     * Object containing address information of the shop
     */
    val address: ShopAddress
)

@Serializable
class ShopAddress(
    /**
     * Street name of the shop location
     * @example "Musterstraße"
     */
    val street: String,
    /**
     * House number as string
     * @example "12"
     */
    val houseNumber: String,
    /**
     * Postal code as string
     * @example "87654"
     */
    val postalCode: String,
    /**
     * City
     * @example "Musterstadt"
     */
    val city: String
)