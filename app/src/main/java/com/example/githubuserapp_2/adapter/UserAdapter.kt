package com.example.githubuserapp_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp_2.R
import com.example.githubuserapp_2.model.UserItems
import kotlinx.android.synthetic.main.list_items.view.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val mData = ArrayList<UserItems>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(item: ArrayList<UserItems>) {
        mData.clear()
        mData.addAll(item)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: UserItems) {
            with(itemView) {

                username.text = userItems.username
                type.text = userItems.type
                Glide.with(itemView.context)
                    .load(userItems.avatar)
                    .into(avatar)
                itemView.setOnClickListener { onItemClickCallback?.onIemClicked(userItems) }

                val anim = AnimationUtils.loadAnimation(context, R.anim.up_recyclerview)
                itemView.animation = anim

            }
        }
    }
}

interface OnItemClickCallback {
    fun onIemClicked(userItems: UserItems)
}
