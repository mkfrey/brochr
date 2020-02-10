package io.l8.brochr

import android.content.Context
import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.l8.brochr.adapters.OfferCategoryViewAdapter
import io.l8.brochr.adapters.OfferListViewAdapter
import io.l8.brochr.models.OfferQueryResult
import io.l8.brochr.models.Shop

class OfferRequest(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val shop: Shop
) : Shop.OfferQueryResultReceiver {
    private var uiHandler = Handler(context.mainLooper)

    fun fetchOffers() {
        shop.fetchOffers(this)
    }

    override fun onQueryResult(result: OfferQueryResult) {
        uiHandler.post {
            if (result.categories?.isEmpty() != false) {
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                recyclerView.adapter = OfferListViewAdapter(result)
            } else {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = OfferCategoryViewAdapter(result)
            }
        }
    }
}