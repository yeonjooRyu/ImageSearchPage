package com.example.imagesearchpage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchItemModel(
    var image: Int,
    var title: String,
    var dateTime: String,
    var isLike: Boolean = false
) :Parcelable