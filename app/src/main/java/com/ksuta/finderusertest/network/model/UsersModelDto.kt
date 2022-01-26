package com.ksuta.finderusertest.network.model

import com.google.gson.annotations.SerializedName

data class UserModelDto(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("acceptRate") val acceptRate: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("profile_image") val profileImage: String,
    @SerializedName("reputation") val reputation: Int,
    @SerializedName("location") val location: String,
    @SerializedName("creation_date") val creationDate: Long,
    )

data class UsersModelDto(
    @SerializedName("items") val itemsUser: List<UserModelDto>
)

data class UserModelTagsDto(
    @SerializedName("name") val name: String
)

data class UsersModelTagsDto(
    @SerializedName("items") val itemsTags: List<UserModelTagsDto>
)