package com.example.imagesearchpage

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchItemModel(
    var title: String,
    var dateTime: String,
    var url: String,
    var isLike: Boolean = false
) :Parcelable