package com.ksuta.finderusertest.network.model

import com.google.gson.annotations.SerializedName

data class UserModelDto(
    @SerializedName("acceptRate") val acceptRate: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("profile_image") val profileImage: String
)

data class UsersModelDto(
    @SerializedName("items") val itemsUser: List<UserModelDto>
)