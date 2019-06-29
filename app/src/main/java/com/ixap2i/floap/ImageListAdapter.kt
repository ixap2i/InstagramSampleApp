package com.ixap2i.floap

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImageListAdapter(val imageUrlList: List<Data>): RecyclerView.Adapter<ImageListAdapter.ImageRecordHolder>() {

    class ImageRecordHolder(val image: ImageView): RecyclerView.ViewHolder(image)

    override fun onBindViewHolder(holder: ImageRecordHolder, position: Int) {
        Picasso.get().load(imageUrlList[position].images.thumbnail!!.url).into(holder.image)
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecordHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_column, parent, false) as ImageView
        return ImageRecordHolder(imageView)
    }
}
