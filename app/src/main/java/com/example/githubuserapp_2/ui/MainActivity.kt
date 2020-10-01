package com.example.githubuserapp_2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp_2.R
import com.example.githubuserapp_2.adapter.OnItemClickCallback
import com.example.githubuserapp_2.adapter.UserAdapter
import com.example.githubuserapp_2.model.UserItems
import com.example.githubuserapp_2.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val STATE_TRUE = "stateTrue"
        const val STATE_FALSE = "stateFalse"
    }

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showTextBase(true)

        supportActionBar?.elevation = 0F

        configMainViewModel(savedInstanceState)
        configRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_TRUE, true)
        outState.putBoolean(STATE_FALSE, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_here)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                showTextBase(false)
                mainViewModel.setUser(query!!, this@MainActivity)
                closeKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configMainViewModel(savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUser().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
                if (savedInstanceState != null) {
                    showLoading(savedInstanceState.getBoolean(STATE_FALSE))
                    showTextBase(savedInstanceState.getBoolean(STATE_FALSE))
                } else {
                    showLoading(false)
                }
            }
            if (it.isEmpty()) {
                if (savedInstanceState != null) {
                    showTextBase(savedInstanceState.getBoolean(STATE_TRUE))
                } else {
                    showTextBase(true)
                }
            }
        })
    }

    private fun configRecyclerView() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object :
            OnItemClickCallback {
            override fun onIemClicked(userItems: UserItems) {
                showSelectedData(userItems)
            }
        })
    }

    private fun showSelectedData(userItems: UserItems) {

        val dataUser = UserItems(
            userItems.username,
            userItems.name,
            userItems.avatar,
            userItems.company,
            userItems.location,
            userItems.type,
            userItems.repository,
            userItems.followers,
            userItems.following
        )

        val intentDetail = Intent(this@MainActivity, DetailUsersActivity::class.java)
        intentDetail.putExtra(DetailUsersActivity.EXTRA_DATA, dataUser)
        startActivity(intentDetail)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showTextBase(state: Boolean) {
        if (state) {
            tvSearchUser.visibility = View.VISIBLE
            lottieNotFound.visibility = View.VISIBLE
        } else {
            tvSearchUser.visibility = View.GONE
            lottieNotFound.visibility = View.GONE
        }
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}