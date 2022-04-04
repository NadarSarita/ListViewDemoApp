package com.example.demoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapp.R
import com.example.demoapp.data.apiresponses.Data
import com.example.demoapp.databinding.ItemLayoutBinding


class UserAdapter(
    private val context: Context,
    private val dataList: ArrayList<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class UserViewHolder(private var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Data) {
            binding.userData = data

            binding.ivAvatar.apply {
                Glide.with(this)
                    .load(data.avatar)
                    .into(this)
            }

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(context)
        val binding: ItemLayoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_layout, viewGroup, false)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is UserViewHolder) {
            val data: Data = dataList[position]
            viewHolder.onBind(data)

        }
    }

    override fun getItemCount() = dataList.size
}