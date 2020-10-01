package com.example.githubuserapp_2.ui

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp_2.R
import com.example.githubuserapp_2.adapter.ViewPagerAdapter
import com.example.githubuserapp_2.model.UserItems
import com.example.githubuserapp_2.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail_users.*
import kotlinx.android.synthetic.main.bg_detail.*

class DetailUsersActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_users)

        showLoading(true)

        val intent = intent.getParcelableExtra(EXTRA_DATA) as UserItems
        val getUsername = intent.username

        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getUsername

        configDetailViewModel(getUsername!!)
        configViewPager()
    }

    private fun configDetailViewModel(getUsername: String) {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(getUsername, this)
        detailViewModel.getDetailUser().observe(this, Observer {

            nameUser.text = it.name
            companyUser.text = it.company
            locationUser.text = it.location
            repositoryUser.text = it.repository.toString()
            followersUser.text = resources.getString(R.string.followers, it.followers)
            followingUser.text = resources.getString(R.string.following, it.following)
            Glide.with(this)
                .load(it.avatar)
                .into(avatarUser)

            val anim = AnimationUtils.loadAnimation(this, R.anim.detail_anim)
            avatarUser.startAnimation(anim)
            detailContainer.startAnimation(anim)
            tabs.startAnimation(anim)
            viewPager.startAnimation(anim)

            showLoading(false)

        })
    }

    private fun configViewPager() {
        val sectionsPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            avatarUser.visibility = View.INVISIBLE
            detailContainer.visibility = View.INVISIBLE
            tabs.visibility = View.INVISIBLE
            viewPager.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.GONE
            avatarUser.visibility = View.VISIBLE
            detailContainer.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}