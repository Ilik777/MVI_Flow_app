package com.example.mvi_flow_application.advertlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi_flow_application.R

class AdvertsAdapter(
    private val advertList: MutableList<AdvertViewData>
) : RecyclerView.Adapter<AdvertsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.advert_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(advertList[position])
    }

    override fun getItemCount(): Int = advertList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdvertList(advertList: List<AdvertViewData>) {
        this.advertList.clear()
        this.advertList.addAll(advertList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.advert_list_item_img)
        private val titleTextView: TextView = itemView.findViewById(R.id.advert_list_item_title)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.advert_list_item_subtitle)

        private lateinit var advert: AdvertViewData

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(advert: AdvertViewData) {
            this.advert = advert
            imageView.setImageDrawable(itemView.context.getDrawable(advert.imageRes))
            titleTextView.text = advert.title
            subtitleTextView.text = advert.subtitle
        }
    }
}