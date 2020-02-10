package io.l8.brochr.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.l8.brochr.R
import io.l8.brochr.models.Offer
import io.l8.brochr.models.OfferQueryResult
import kotlinx.android.synthetic.main.offer_category.view.*

class OfferCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(categoryName: String, categoryId: Int, offers: ArrayList<Offer>?) = with(itemView) {

        category_name?.text = categoryName

        val offersOfCategory = arrayListOf<Offer>()
        offers?.filterTo(offersOfCategory) { offer -> offer.categoryIndices?.contains(categoryId) ?: false }

        category_offer_items.adapter = OfferListViewAdapter(OfferQueryResult(offersOfCategory))
        category_offer_items.layoutManager = GridLayoutManager(context, 2)

        itemView.setOnClickListener {
            if (itemView.category_offer_items.visibility != View.GONE) {
                deflateOffers()
            } else {
                inflateOffers()
            }
        }
    }

    fun inflateOffers() = with(itemView) {
        itemView.category_offer_items.visibility = View.VISIBLE
        itemView.category_name.setCompoundDrawablesRelativeWithIntrinsicBounds(
            resources.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp, null),
            null,
            null,
            null
        )
    }

    fun deflateOffers() = with(itemView) {
        itemView.category_offer_items.visibility = View.GONE
        itemView.category_name.setCompoundDrawablesRelativeWithIntrinsicBounds(
            resources.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp, null),
            null,
            null,
            null
        )
    }
}

class OfferCategoryViewAdapter(offerQueryResult: OfferQueryResult) : RecyclerView.Adapter<OfferCategoryViewHolder>() {
    private val offers: ArrayList<Offer>? = offerQueryResult.offers
    private val categories: Array<String>? = offerQueryResult.categories

    override fun getItemCount(): Int {
        return categories?.count() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryContainer = layoutInflater.inflate(R.layout.offer_category, parent, false)
        return OfferCategoryViewHolder(categoryContainer)
    }

    override fun onBindViewHolder(holder: OfferCategoryViewHolder, position: Int) {
        categories?.get(position)?.let {
            holder.bind(it, position, offers)
        }
    }
}