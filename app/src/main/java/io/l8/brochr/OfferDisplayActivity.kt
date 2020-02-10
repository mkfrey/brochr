package io.l8.brochr

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.l8.brochr.adapters.EmptyRecyclerViewAdapter
import io.l8.brochr.adapters.EmptyRecyclerViewHolder
import io.l8.brochr.adapters.OfferCategoryViewAdapter
import io.l8.brochr.adapters.OfferListViewAdapter
import io.l8.brochr.models.Shop
import kotlinx.android.synthetic.main.activity_offers.*

class ShopOfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        val shop = intent.getParcelableExtra<Shop>("shop")!!

        supportActionBar?.title = shop.name
        offer_view.adapter = EmptyRecyclerViewAdapter()
        offer_view.layoutManager = LinearLayoutManager(this)

        OfferRequest(this, offer_view, shop).fetchOffers()
    }
}