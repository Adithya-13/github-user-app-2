package com.example.githubuserapp_2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserItemsDetail(
    var username: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var company: String? = null,
    var location: String? = null,
    var type: String? = null,
    var repository: Int = 0,
    var followers: Int = 0,
    var following: Int = 0
) : Parcelable