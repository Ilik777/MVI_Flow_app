package com.example.mvi_flow_application.advertlist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvi_flow_application.R
import com.example.mvi_flow_application.advertlist.presentation.model.AdvertViewData

class AdvertsAdapter(
        private val listener: AdvertClickedCallBack
) : RecyclerView.Adapter<AdvertsAdapter.ViewHolder>() {

    private val advertList: MutableList<AdvertViewData> = mutableListOf()

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.advert_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(advertList[position])
        holder.setItemClickListener(listener)
    }

    override fun getItemCount(): Int = advertList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdvertList(advertList: List<AdvertViewData>) {
        this.advertList.clear()
        this.advertList.addAll(advertList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this.advertList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        private val imageView: ImageView = itemView.findViewById(R.id.advert_list_item_img)
        private val titleTextView: TextView = itemView.findViewById(R.id.advert_list_item_title)
        private val subtitleTextView: TextView = itemView.findViewById(R.id.advert_list_item_subtitle)
        private val firedButton: Button = itemView.findViewById(R.id.advert_list_item_fired_button)

        private lateinit var advert: AdvertViewData

        private var listener: AdvertClickedCallBack? = null

        override fun onClick(v: View?) {
            when (v?.id) {
                firedButton.id -> {
                    listener?.onFireButtonClicked(advert)
                }
                else -> {
                    listener?.onAdvertItemClicked(advert)
                }
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(advert: AdvertViewData) {
            this.advert = advert
            imageView.setImageDrawable(itemView.context.getDrawable(advert.imageRes))
            titleTextView.text = advert.title
            subtitleTextView.text = advert.subtitle
            itemView.setOnClickListener(this)
            firedButton.setOnClickListener(this)
        }

        fun setItemClickListener(listener: AdvertClickedCallBack) {
            this.listener = listener
        }
    }
}