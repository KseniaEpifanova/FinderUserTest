package com.ksuta.finderusertest.screens.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ksuta.finderusertest.R
import com.ksuta.finderusertest.databinding.ItemUserBinding
import com.ksuta.finderusertest.screens.search.UserModel

class HomeUsersAdapter(context: Context, val listener: ItemCallback) :
    PagingDataAdapter<UserModel, HomeNewsViewHolder>(ArticleDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewsViewHolder {
        return HomeNewsViewHolder(
            layoutInflater.inflate(
                R.layout.item_user, parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: HomeNewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class HomeNewsViewHolder(itemView: View, listener: ItemCallback) :
    RecyclerView.ViewHolder(itemView) {
    private var listener: ItemCallback? = null
    private val viewBinding by viewBinding(ItemUserBinding::bind)

    init {
        this.listener = listener
    }

    fun bind(userModel: UserModel?) {

        with(viewBinding) {
            card.setOnClickListener { listener?.onClick(userModel?.userId) }
            title.text = userModel?.displayName
        }
    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<UserModel>() {

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.displayName == newItem.displayName && oldItem.acceptRate == newItem.acceptRate
    }
}

interface ItemCallback {
    fun onClick(userId: Int?)
}