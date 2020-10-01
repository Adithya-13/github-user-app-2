package com.example.githubuserapp_2.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp_2.model.UserItemsFollowing
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    val listUsersFollowing = MutableLiveData<ArrayList<UserItemsFollowing>>()

    fun setFollowingUser(getUsername: String?, context: Context) {

        val listFollowingItems = ArrayList<UserItemsFollowing>()

        val apiKey = "b2379b486548870cbfd0bebdd0cea8e43bdb078e"
        val url = "https://api.github.com/users/$getUsername/following"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {

                    val result = String(responseBody!!)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {

                        val users = responseArray.getJSONObject(i)
                        val userItemsFollowing =
                            UserItemsFollowing()

                        userItemsFollowing.username = users.getString("login")
                        userItemsFollowing.type = users.getString("type")
                        userItemsFollowing.avatar = users.getString("avatar_url")

                        listFollowingItems.add(userItemsFollowing)

                    }

                    listUsersFollowing.postValue(listFollowingItems)
                } catch (e: Exception) {
                    Toast.makeText(context, "Unable to Connect", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(context, "Unable to Connect: $statusCode", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getFollowingUser(): LiveData<ArrayList<UserItemsFollowing>> {
        return listUsersFollowing
    }
}