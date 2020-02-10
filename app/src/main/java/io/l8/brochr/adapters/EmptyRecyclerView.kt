package io.l8.brochr.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EmptyRecyclerViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!)

class EmptyRecyclerViewAdapter: RecyclerView.Adapter<EmptyRecyclerViewHolder>() {
    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: EmptyRecyclerViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyRecyclerViewHolder {
        return EmptyRecyclerViewHolder(null)
    }
}