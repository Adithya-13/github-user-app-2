package com.example.githubuserapp_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp_2.R
import com.example.githubuserapp_2.model.UserItemsFollowing
import kotlinx.android.synthetic.main.list_items.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    private val mFollowingData = ArrayList<UserItemsFollowing>()

    fun setFollowingData(item: ArrayList<UserItemsFollowing>) {
        mFollowingData.clear()
        mFollowingData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun getItemCount(): Int = mFollowingData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mFollowingData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItemsFollowing: UserItemsFollowing) {
            with(itemView) {

                username.text = userItemsFollowing.username
                type.text = userItemsFollowing.type
                Glide.with(itemView.context)
                    .load(userItemsFollowing.avatar)
                    .into(avatar)

                val anim = AnimationUtils.loadAnimation(context, R.anim.up_recyclerview)
                itemView.animation = anim
            }
        }
    }
}