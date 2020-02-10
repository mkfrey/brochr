package io.l8.brochr.api.rewe.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OfferQueryResult(
    /**
     * Array of the offers
     */
    val items: ArrayList<Offer>,
    /**
     * Metadata for the offers, e.g. the product categories
     */
    val _meta: OfferQueryMetaInformation
)

@Serializable
class OfferQueryMetaInformation(
    /**
     * Array of the product/offer categories
     */
    val categories: Array<OfferCategory>
)

@Serializable
class OfferCategory(
    /**
     * ID of the product category
     * @example "1.4"
     */
    val id: String,
    /**
     * ???
     * Might be the value before the dot in the product category. Probably allows for a complex
     * hierarchy
     * @example 1
     */
    val level: Int, // ??
    /**
     * Friendly name of the category
     * @example "Tiefkühl"
     */
    val name: String
)

@Serializable
class Offer(
    /**
     * ID of the offer (composition of productId and shop id)
     * @example "3095809_5_466549"
     */
    val id: String,
    /**
     * ???
     * Appears to be the same as productId
     * @example "3095809"
     */
    val nan: String,
    /**
     * Product ID
     * Not sure how IDs of offers with multiple flavors of the same product are implemented
     * @example "3095809"
     */
    val productId: String,
    /**
     * Brand/manufacturer of the product
     * @example "REWE"
     */
    val brand: String? = null,
    /**
     * Name(s) of the product which are part of the offer
     * @example "REWE Beste Wahl American Ice Cream"
     */
    val name: String,
    /**
     *  Quantity and the quantity unit of the product
     *  @example "je 500-ml-Becher"
     */
    val quantityAndUnit: String,
    /**
     * Offer price of the product
     * @example 1.99
     */
    val price: Float,
    /**
     * Currency of the price, probably according to ISO 4217:2015
     * @example "EUR"
     */
    val currency: String,
    /**
     * Product discount in percent
     * @example "-20%"
     */
    val discount: String? = null,

    /**
     * Array containing the product category IDs whose names are provided in the metadata
     * @example ["1.2"]
     */
    val categoryIDs: Array<String>, // Array of the products category IDs
    /**
     * Additional product information, to be used within a description
     * @example "versch. Sorten,"
     */
    val additionalInformation: String? = null, // Additional product information, to be used within a description
    /**
     * Further product links, which consist only of images
     */
    val _links: OfferLinks
)


/** The links are only product images. They mostly have the same URLs and are all very big,
 * but can be requested resized by appending "?resize=XXXpx:XXXpx"
 **/
@Serializable
class OfferLinks(
    @SerialName("image:digital") var image: OfferLinkHref? = null,
    @SerialName("image:m") var imageM: OfferLinkHref? = null,
    @SerialName("image:xl") var imageXl: OfferLinkHref? = null
)

@Serializable
class OfferLinkHref(
    /**
     * Actual link of the product image
     */
    val href: String
)

