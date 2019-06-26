package com.ixap2i.floap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ixap2i.floap.databinding.ImageColumnBindingImpl
import com.squareup.picasso.Picasso

class ImageListAdapter(val imageUrlList: List<Data>): RecyclerView.Adapter<ImageListAdapter.ImageRecordHolder>() {
    override fun onBindViewHolder(holder: ImageRecordHolder, position: Int) {
        holder.apply {
            bind(imageUrlList, position)
        }
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecordHolder {
        return ImageRecordHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.image_column, parent, false)
        )
    }


    class ImageRecordHolder(private val binding: ImageColumnBindingImpl): RecyclerView.ViewHolder(binding.root) {
        fun bind(datas: List<Data>, position: Int) {
            binding.apply {
                Picasso.get().load(datas[position].images.thumbnail!!.url).into(binding.thumbnail)
            }
        }
    }
}
