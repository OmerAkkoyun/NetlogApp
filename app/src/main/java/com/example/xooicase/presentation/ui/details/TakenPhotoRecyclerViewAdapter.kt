package com.example.xooicase.presentation.ui.details

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.xooicase.R
import com.example.xooicase.databinding.ItemTakenImageHolderBinding

class TakenPhotoRecyclerViewAdapter(
    private val uriList: ArrayList<String>
) : RecyclerView.Adapter<TakenPhotoRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_taken_image_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(uriList[position])
    }

    override fun getItemCount() = uriList.size


    class ViewHolder(val binding: ItemTakenImageHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: String) {
            binding.imgPhoto.setImageURI(Uri.parse(uri))
        }
    }

}