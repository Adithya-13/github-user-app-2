package com.example.githubuserapp_2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp_2.R
import com.example.githubuserapp_2.model.UserItemsFollowers
import kotlinx.android.synthetic.main.list_items.view.*

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    private val mFollowersData = ArrayList<UserItemsFollowers>()

    fun setFollowersData(item: ArrayList<UserItemsFollowers>) {
        mFollowersData.clear()
        mFollowersData.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun getItemCount(): Int = mFollowersData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mFollowersData[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItemsFollowers: UserItemsFollowers) {
            with(itemView) {

                username.text = userItemsFollowers.username
                type.text = userItemsFollowers.type
                Glide.with(itemView.context)
                    .load(userItemsFollowers.avatar)
                    .into(avatar)

                val anim = AnimationUtils.loadAnimation(context, R.anim.up_recyclerview)
                itemView.animation = anim

            }
        }
    }
}