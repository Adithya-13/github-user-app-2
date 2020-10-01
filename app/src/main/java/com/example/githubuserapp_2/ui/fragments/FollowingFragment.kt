package com.example.githubuserapp_2.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp_2.R
import com.example.githubuserapp_2.adapter.FollowingAdapter
import com.example.githubuserapp_2.model.UserItems
import com.example.githubuserapp_2.ui.DetailUsersActivity
import com.example.githubuserapp_2.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    private lateinit var followingAdapter: FollowingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent =
            activity?.intent?.getParcelableExtra(DetailUsersActivity.EXTRA_DATA) as UserItems
        val getUsername = intent.username

        if (getUsername != null) {
            configFollowingViewModel(getUsername)
        }

        configRecyclerView()
    }

    private fun configFollowingViewModel(getUsername: String) {
        val followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        followingViewModel.setFollowingUser(getUsername, activity!!)
        followingViewModel.getFollowingUser().observe(activity!!, Observer {
            if (it != null) {
                followingAdapter.setFollowingData(it)
                notFound.visibility = View.GONE
            }
            if (it.isEmpty()) {
                notFound.visibility = View.VISIBLE
            }
        })
    }

    private fun configRecyclerView() {
        followingAdapter = FollowingAdapter()
        followingAdapter.notifyDataSetChanged()

        followingRecyclerView.layoutManager = LinearLayoutManager(activity)
        followingRecyclerView.adapter = followingAdapter
        followingRecyclerView.setHasFixedSize(true)
    }
}