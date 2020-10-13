package io.l8.brochr.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.l8.brochr.R
import io.l8.brochr.ShopOfferActivity
import io.l8.brochr.models.Shop
import io.l8.brochr.models.ShopSearch
import kotlinx.android.synthetic.main.shop_item.view.*


class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(shop: Shop?, onClickListener: OnShopClickListener) = with(itemView) {

        shop?.let {
            shop_name?.text = it.name
            shop_street?.text = it.street
            shop_city?.text = "${it.postalCode} ${it.city}"
            Glide.with(context).load(it.imageUrl).into(shop_image)
        }

        itemView.setOnClickListener {
            onClickListener.onShopClick(shop)
        }
    }

    interface OnShopClickListener {
        fun onShopClick(shop: Shop?)
    }
}


class ShopSearchViewAdapter(private val context: Context) : RecyclerView.Adapter<ShopViewHolder>(), SearchView.OnQueryTextListener,
    ShopViewHolder.OnShopClickListener, ShopSearch.ShopQueryResultReceiver {
    private var shops: ArrayList<Shop> = arrayListOf()
    private var uiHandler = Handler(context.mainLooper)

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            clearShopList()
            notifyUiHandlerOfChange()
            val reweSearch = io.l8.brochr.api.rewe.model.ShopSearch()
            reweSearch.getShops(it, this)
            val edekaSearch = io.l8.brochr.api.edeka.model.ShopSearch()
            edekaSearch.getShops(it, this)
            return true
        }
        return false
    }

    override fun onQueryResult(result: ArrayList<Shop>?) {
        result?.let {
            shops.addAll(it)
        }

        notifyUiHandlerOfChange()    }

    private fun clearShopList() {
        shops.clear()
        notifyUiHandlerOfChange()
    }

    private fun notifyUiHandlerOfChange() {
        uiHandler.post {
            notifyDataSetChanged()
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    /* Implementation of RecyclerView Adapter */
    override fun getItemCount(): Int {
        return shops.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val shopItem = layoutInflater.inflate(R.layout.shop_item, parent, false)
        return ShopViewHolder(shopItem)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        shops[position]?.let {
            holder.bind(it, this)
        }
    }

    override  fun onShopClick(shop: Shop?) {
        shop?.let {
            val offerViewIntend = Intent(context, ShopOfferActivity::class.java)
            offerViewIntend.putExtra("shop", it)
            context.startActivity(offerViewIntend)
        }
    }
}