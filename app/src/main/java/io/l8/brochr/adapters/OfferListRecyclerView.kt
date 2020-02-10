package io.l8.brochr.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.l8.brochr.R
import io.l8.brochr.models.Offer
import io.l8.brochr.models.OfferQueryResult
import kotlinx.android.synthetic.main.offer_item.view.*

class OfferListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(offer: Offer?) = with(itemView) {
        offer?.let {
            offer_name?.text = it.name
            offer_price?.text = "${it.price} ${it.priceUnit}"
            offer_discount?.text = it.discountOrRegularPrice
            Glide.with(context).load(it.thumbnailUrl).into(offer_image)
        }
    }
}

class OfferListViewAdapter(offerQueryResult: OfferQueryResult) : RecyclerView.Adapter<OfferListViewHolder>() {
    private var offers: ArrayList<Offer>? = offerQueryResult.offers

    override fun getItemCount(): Int {
        return offers?.count() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val offerItem = layoutInflater.inflate(R.layout.offer_item, parent, false)
        return OfferListViewHolder(offerItem)
    }

    override fun onBindViewHolder(holder: OfferListViewHolder, position: Int) {
        offers?.get(position)?.let {
            holder.bind(it)
        }
    }
}