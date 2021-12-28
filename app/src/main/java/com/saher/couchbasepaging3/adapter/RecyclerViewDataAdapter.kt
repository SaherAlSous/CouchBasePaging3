package com.saher.couchbasepaging3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saher.couchbasepaging3.R
import com.saher.couchbasepaging3.db.Photo
import com.saher.couchbasepaging3.util.getProgressDrawable
import com.saher.couchbasepaging3.util.loadImage

/**
 * This will take the paging data from the adapter and convert it again for the recycler view
 */
class RecyclerViewDataAdapter(): PagingDataAdapter<Photo, PhotoHolder>(
    diffCallback = DiffCallback
) {
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item_view, parent, false)
        return PhotoHolder(view)
    }

}

class PhotoHolder(view: View):
    RecyclerView.ViewHolder(view) {

    private val imageView: ImageView = view.findViewById(R.id.ImageView)
    private lateinit var photo: Photo

    fun bind(photo : Photo){
        this.photo = photo
        /**
         * loading image using the ImageView Extension
         * getting the context from the image_view context.
         */
        imageView.loadImage(photo.url_s, getProgressDrawable(imageView.context))
    }

}

object DiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}