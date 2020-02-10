package io.l8.brochr.api.edeka.api_model

import kotlinx.serialization.Serializable
import kotlin.collections.ArrayList

@Serializable
class Offer(
    /**
     * Internal offer ID. Appears to be unique
     * @example 2537855
     */
    val angebotid: Int,
    /**
     * Name(s) of the product which are part of the offer
     * @example "EDEKA Recycling Taschentücher"
     */
    val titel: String,
    /**
     * Price of the product. Since no unit is given,
     * it is assumed that pricing is always in €
     * @example 0.95
     */
    val preis: Float,
    /**
     * Product description. Contains additional information about
     * the product(s) such as the quantity or varations
     * @example "100er Box"
     */
    val beschreibung: String?,
    /**
     * Basic price of the product
     * @example "100 ml = 0,51/0,31 €"
     */
    val basicPrice: String?,
    /**
     * ???
     * Maybe this is an indicator whether the offer is available nationwide
     * @example: false
     */
    val national: Boolean?,
    /**
     * URL of a product image with a size of 90px in at least one dimension
     * depending on the image ratio
     */
    val bild_web90: String,
    /**
     * URL of a product image with a size of 130px in at least one dimension
     * depending on the image ratio
     */
    val bild_web130: String,
    /**
     * URL of a product image with a size of 1136px in at least one dimension
     * depending on the image ratio
     */
    val bild_app: String,
    /**
     * Name of the product category
     * @example "Drogerie"
     */
    val warengruppe: String?,
    /**
     * ID of the product category
     * @example 9
     */
    val warengruppeid: Int?
    /**
     * Start date of the offer validity as unix timestamp
     * @example 1580511600000
     */
    //val gueltig_bis: Long
)

@Serializable
class OfferQueryResult(
    val docs: ArrayList<Offer>
    /**
     * Start date of the offer validity as unix timestamp
     * @example 1580079600000
     */
    //val gueltig_von: Long,
    /**
     * End date of the offer validity as unix timestamp
     * @example 1580511600000
     */
    //val gueltig_bis: Long
)

