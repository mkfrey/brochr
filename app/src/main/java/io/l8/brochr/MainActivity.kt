package io.l8.brochr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.l8.brochr.adapters.ShopSearchViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shopSearchViewAdapter =
            ShopSearchViewAdapter(this)

        shop_view.layoutManager = LinearLayoutManager(this)
        shop_view.adapter = shopSearchViewAdapter

        shop_search.setOnQueryTextListener(shopSearchViewAdapter)
    }
}
