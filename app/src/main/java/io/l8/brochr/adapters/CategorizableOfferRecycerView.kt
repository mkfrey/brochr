package io.l8.brochr.adapters

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.l8.brochr.R
import io.l8.brochr.models.Offer
import io.l8.brochr.models.OfferQueryResult
import io.l8.brochr.models.Shop
import kotlinx.android.synthetic.main.offer_list_category.view.*
import kotlinx.android.synthetic.main.offer_list_item.view.*


class CategorizableOfferRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindCategoryItem(
        categoryName: String,
        categoryId: Int,
        clickReceiver: CategoryItemClickReceiver
    ) = with(itemView) {
        category_name?.text = categoryName
        itemView.setOnClickListener {
            clickReceiver.onCategoryItemClick(categoryId)
        }
    }

    fun bindOfferItem(offer: Offer) = with(itemView) {
        offer_name?.text = offer.name
        offer_price?.text = "${offer.price} ${offer.priceUnit}"
        offer_discount?.text = offer.discountOrRegularPrice
        Glide.with(context).load(offer.thumbnailUrl).into(offer_image)
    }

    interface CategoryItemClickReceiver {
        fun onCategoryItemClick(categoryId: Int)
    }
}

class CategorizableOfferRecycerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<CategorizableOfferRecyclerViewHolder>(), Shop.OfferQueryResultReceiver,
    CategorizableOfferRecyclerViewHolder.CategoryItemClickReceiver {
    private var offerQueryResult: OfferQueryResult = OfferQueryResult.None;
    private var expandedCategory: Int? = null;
    private var expandedCategoryOfferCount: Int = 0;
    private var uiHandler = Handler(context.mainLooper)

    override fun getItemCount(): Int {
        offerQueryResult.let { result ->
            return when (result) {
                is OfferQueryResult.None ->
                    0;
                is OfferQueryResult.Error ->
                    0;
                is OfferQueryResult.NonCategorized ->
                    result.offers.count();
                is OfferQueryResult.Categorized ->
                    result.categories.count() + expandedCategoryOfferCount;
            }
        }
    }

    override fun onBindViewHolder(holder: CategorizableOfferRecyclerViewHolder, position: Int) {
        if (getItemViewType(position) == ItemViewType.CATEGORY.value) {
            val categoryIndex = getCategoryIndexByPosition(position);
            holder.bindCategoryItem(
                (offerQueryResult as OfferQueryResult.Categorized).categories[getCategoryIndexByPosition(
                    position
                )], categoryIndex, this
            )
        } else {
            offerQueryResult.let { result ->
                when (result) {
                    is OfferQueryResult.NonCategorized ->
                        holder.bindOfferItem(result.offers[position])
                    is OfferQueryResult.Categorized ->
                        holder.bindOfferItem(result.offers.filter {
                            it.categoryIndices?.contains(
                                expandedCategory!!
                            ) ?: false
                        }[position - (expandedCategory!! + 1)])
                    else -> {
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategorizableOfferRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryContainer = when (viewType) {
            ItemViewType.CATEGORY.value -> layoutInflater.inflate(
                R.layout.offer_list_category,
                parent,
                false
            )
            else -> layoutInflater.inflate(R.layout.offer_list_item, parent, false)
        }

        return CategorizableOfferRecyclerViewHolder(categoryContainer)
    }

    override fun getItemViewType(position: Int): Int {
        offerQueryResult.let { result ->
            return when (result) {
                is OfferQueryResult.Categorized -> {
                    expandedCategory?.let { expandedCategory ->
                        if (position <= expandedCategory || position > expandedCategory + expandedCategoryOfferCount) {
                            ItemViewType.CATEGORY.value
                        } else {
                            ItemViewType.OFFER.value;
                        }
                    } ?: ItemViewType.CATEGORY.value
                }
                else ->
                    ItemViewType.OFFER.value;
            }
        }
    }

    override fun onQueryResult(result: OfferQueryResult) {
        offerQueryResult = result
        expandedCategory = null
        expandedCategoryOfferCount = 0
        uiHandler.post{
            notifyDataSetChanged()
        }
    }

    override fun onCategoryItemClick(categoryId: Int) {
        setExpandedCategory(categoryId)
    }

    private fun updateExpandedCategoryOfferCount() {
        expandedCategory?.let { expandedCategory ->
            offerQueryResult.let { result ->
                when (result) {
                    is OfferQueryResult.Categorized -> {
                        expandedCategoryOfferCount = result.offers.filter { offer ->
                            offer.categoryIndices?.contains(expandedCategory) ?: false
                        }.count();
                    }
                }
            }
        } ?: kotlin.run { expandedCategoryOfferCount = 0 }
    }

    private fun setExpandedCategory(newExpandedCategory: Int) {
        if (newExpandedCategory == expandedCategory) {
            expandedCategory = null
            updateExpandedCategoryOfferCount();
            uiHandler.post { notifyDataSetChanged() }
            return
        };

        // Hide the currently shown offers
        /*expandedCategory?.let { ec ->
            if (expandedCategoryOfferCount > 0) {

                expandedCategory = null;
                updateExpandedCategoryOfferCount();
                uiHandler.post {notifyItemRangeRemoved(ec + 1, expandedCategoryOfferCount)}
            }
        }*/

        expandedCategory = newExpandedCategory;
        updateExpandedCategoryOfferCount();

        if (expandedCategoryOfferCount > 0) {
            //uiHandler.post {notifyItemRangeInserted(newExpandedCategory + 1, expandedCategoryOfferCount)}
            uiHandler.post { notifyDataSetChanged() }
        }
    }

    private fun getCategoryIndexByPosition(position: Int): Int {
        expandedCategory?.let { ec ->
            if (ec >= position) return position;
        }
        return position - expandedCategoryOfferCount;
    }

    companion object {
        public enum class ItemViewType(val value: Int) {
            CATEGORY(0),
            OFFER(1)
        }
    }
}