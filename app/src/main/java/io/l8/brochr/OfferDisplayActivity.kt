package io.l8.brochr

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.l8.brochr.adapters.*
import io.l8.brochr.models.Shop
import kotlinx.android.synthetic.main.activity_offers.*

class ShopOfferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        val shop = intent.getParcelableExtra<Shop>("shop")!!

        val adapter = CategorizableOfferRecycerViewAdapter(this)

        supportActionBar?.title = shop.name
        offer_view.adapter = adapter
        offer_view.layoutManager = LinearLayoutManager(this)

        shop.fetchOffers(adapter)
    }
}