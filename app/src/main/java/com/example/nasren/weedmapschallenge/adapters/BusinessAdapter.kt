package com.example.nasren.weedmapschallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasren.weedmapschallenge.viewmodels.FeedViewModel
import com.example.nasren.weedmapschallenge.R
import com.example.nasren.weedmapschallenge.models.Business
import com.example.nasren.weedmapschallenge.models.Business.Companion.DIFF_CALLBACK
import com.example.nasren.weedmapschallenge.models.Review
import com.example.nasren.weedmapschallenge.utils.setImage
import kotlinx.android.synthetic.main.view_holder_business.view.*

class BusinessAdapter(val feedViewModel: FeedViewModel) : PagedListAdapter<Business, BusinessAdapter.BusinessViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        return BusinessViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_business, parent, false))
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = getItem(position)

        if (business?.image_url == null || business.name == null)
            return

        holder.bind(business)
    }

    inner class BusinessViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), LifecycleOwner {

        private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }

        fun bind(business: Business) {
            val position = adapterPosition

            if (position < 0 || position >= itemCount)
                return

            lifecycleRegistry.markState(Lifecycle.State.STARTED)

            itemView.ivBusinessImage.setImage(business.image_url)
            itemView.tvTitle.text = business.name
            setTopReview(position)
        }

        /*
            Pulls the top review from ReviewsRepository
            and sets the item's review text if it wasn't already pulled
         */
        private fun setTopReview(position: Int) {

            val item = getItem(position) ?: return

            if (item.topReview == null) {
                feedViewModel.fetchTopReview(item.id)
                    .observe(this, Observer<Review> { review ->

                        if (position < 0 || position >= itemCount)
                            return@Observer

                        item.topReview = review.text
                        itemView.tvReview.text = review.text
                    })
            }
            else {
                itemView.tvReview.text = item.topReview
            }
        }
    }
}