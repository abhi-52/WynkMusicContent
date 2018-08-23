package com.example.ui

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.wynkbasic.content.db.entities.Item

class HomeAdapter(val context: Context?) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private var items: List<Item>? = null

    private val inflator = LayoutInflater.from(context)

    fun setData(items: List<Item>?) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = inflator.inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tvTitle.text = items?.get(position)?.title
        holder.tvIndex.text = position.toString()
        holder.tvSubTitle.text = items?.get(position)?.getSubTitle()
        Glide.with(holder.imageView.context).load(items?.get(position)?.getSmallImage()).into(holder.imageView)
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: AppCompatImageView = itemView.findViewById(R.id.iv_song)
        lateinit var tvTitle: TextView
        lateinit var tvSubTitle: TextView
        lateinit var tvIndex: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tv_item_title)
            tvSubTitle = itemView.findViewById(R.id.tv_item_subTitle)
            tvIndex = itemView.findViewById(R.id.tv_index)
        }
    }
}