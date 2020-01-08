package com.example.imgur.view.images

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imgur.R
import java.util.*

class ImageAdapter internal constructor(
    private val pathImages: List<String>?,
    val onClick:(View, Int, Bitmap)->Unit) :
    RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    //store only tapped images URI
    private val pathTappedImages: MutableList<String> = ArrayList()

    inner class ImageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        init {
            itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                        // replace image with tapped to loading_indicator.png
                        Glide.with(imageView)
                            .load(R.drawable.loading_indicator)
                            .into(imageView)
                            .waitForLayout()
                        // add URI of image with tapped
                        pathImages?.let {
                            pathTappedImages.add(it[position])
                            onClick(itemView, position, bitmap)
                        }
                    }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.image_layout, parent, false)
        return ImageHolder(view)
    }

    override fun onBindViewHolder(
        holder: ImageHolder,
        position: Int
    ) {
        // check if current ImageHolder is tapped (store URI in pathTappedImages)
        pathImages?.let {
            if (pathTappedImages.contains(it[position])) {
                val imageView = holder.imageView
                // set loading_indicator.png instead of real image
                Glide.with(imageView)
                    .load(R.drawable.loading_indicator)
                    .into(holder.imageView)
                    .waitForLayout()
            } else {
                // set correct image
                val imageView = holder.imageView
                Glide.with(imageView)
                    .load(pathImages[position])
                    .into(holder.imageView)
                    .waitForLayout()
            }
        }
    }

    override fun getItemCount(): Int {
        return pathImages?.size ?: 0
    }
}
