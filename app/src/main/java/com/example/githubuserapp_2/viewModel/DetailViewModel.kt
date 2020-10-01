package com.example.githubuserapp_2.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp_2.model.UserItemsDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {

    val listUsersDetail = MutableLiveData<UserItemsDetail>()

    fun setDetailUser(getUsername: String?, context: Context) {

        val apiKey = "b2379b486548870cbfd0bebdd0cea8e43bdb078e"
        val url = "https://api.github.com/users/$getUsername"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                try {

                    val result = String(responseBody!!)
                    val responseObject = JSONObject(result)
                    val userItemsDetail =
                        UserItemsDetail()

                    userItemsDetail.username = responseObject.getString("login")
                    userItemsDetail.name = responseObject.getString("name")
                    userItemsDetail.avatar = responseObject.getString("avatar_url")
                    userItemsDetail.company = responseObject.getString("company")
                    userItemsDetail.location = responseObject.getString("location")
                    userItemsDetail.repository = responseObject.getInt("public_repos")
                    userItemsDetail.followers = responseObject.getInt("followers")
                    userItemsDetail.following = responseObject.getInt("following")

                    listUsersDetail.postValue(userItemsDetail)

                } catch (e: Exception) {
                    Toast.makeText(context, "Unable to Connect", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                Toast.makeText(context, "Unable to Connect: $statusCode", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDetailUser(): LiveData<UserItemsDetail> {
        return listUsersDetail
    }

}