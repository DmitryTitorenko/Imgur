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

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    inner class ImageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        // Your holder should contain a member variable
// for any view that will be set as you render a row
        var imageView: ImageView

        // We also create a constructor that accepts the entire item row
// and does the view lookups to find each subview
        init { // Stores the itemView in a public final member variable that can be used
// to access the context from any ViewHolder instance.
            imageView = itemView.findViewById(R.id.imageView)
            // Attach a click listener to the entire grid view
            itemView.setOnClickListener { v: View? ->
                if (onClick != null) {
                    val position = adapterPosition // gets grid position
                    if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
//get bitmap from on clicked imageView
                        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                        // replace image with tapped to loading_indicator.png
                        Glide.with(imageView)
                            .load(R.drawable.loading_indicator)
                            .into(imageView)
                            .waitForLayout()
                        // add URI of image with tapped
                        pathTappedImages.add(pathImages!![position])
                        onClick(itemView, position, bitmap)
                    }
                }
            }
        }
    }

    // inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val view: View = inflater.inflate(R.layout.image_layout, parent, false)
        // Return a new holder instance
        return ImageHolder(view)
    }

    // Get the data model based on position and
// set the view attributes based on the data
    override fun onBindViewHolder(
        holder: ImageHolder,
        position: Int
    ) { // check if current ImageHolder is tapped (store URI in pathTappedImages)
        if (pathTappedImages.contains(pathImages!![position])) {
            val imageView = holder.imageView
            // set loading_indicator.png instead of real image
            Glide.with(imageView)
                .load(R.drawable.loading_indicator)
                .into(holder.imageView)
                .waitForLayout()
        } else { // set correct image
            val imageView = holder.imageView
            Glide.with(imageView)
                .load(pathImages[position])
                .into(holder.imageView)
                .waitForLayout()
        }
    }

    override fun getItemCount(): Int {
        return pathImages?.size ?: 0
    }
}