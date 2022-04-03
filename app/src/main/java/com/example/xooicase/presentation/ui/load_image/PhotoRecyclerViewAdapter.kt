package com.example.xooicase.presentation.ui.load_image

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.xooicase.R
import com.example.xooicase.databinding.ItemImageHolderBinding
import com.example.xooicase.presentation.interfaces.IOnDeleteItemClickListener

class PhotoRecyclerViewAdapter(
    private val uriList: ArrayList<String>,
    private val iOnDeleteItemClickListener: IOnDeleteItemClickListener
) : RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_image_holder, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(uriList[position])
        holder.binding.imgDeleteItem.setOnClickListener {
            iOnDeleteItemClickListener.onDeleteItemClickListener(position)
        }
    }

    override fun getItemCount() = uriList.size


    class ViewHolder(val binding: ItemImageHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: String) {
            binding.imgPhoto.setImageURI(Uri.parse(uri))
        }
    }

}