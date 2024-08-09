package com.suitmedia.portalsuitmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suitmedia.portalsuitmedia.R
import com.suitmedia.portalsuitmedia.data.remote.response.DataItem
import com.suitmedia.portalsuitmedia.databinding.UserItemBinding

class UserAdapter: PagingDataAdapter<DataItem, UserAdapter.ListViewHolder>(diffCallback) {

    var onClick: ((DataItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user!!, onClick)
    }

    class ListViewHolder(private var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("StringFormatInvalid")
        fun bind(user: DataItem, onClick: ((DataItem) -> Unit)?) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(ivProfilePicture)
                tvEmail.text = user.email
                tvName.text = itemView.context.getString(R.string.user_name_format, user.firstName, user.lastName)

                itemView.setOnClickListener {
                    onClick?.invoke(user)
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}