package com.ksuta.finderusertest.screens.search

data class UserModel(
    val userId: Int,
    val acceptRate: String?,
    val displayName: String?,
    val profileImage: String?,
    val reputation: Int?,
    val location: String?,
    val creationDate: Long?
)

data class UsersModel(
    val itemsUser: List<UserModel>?
)

data class UserTagsModel(
    val name: String
)

data class UsersTagsModel(
    val itemsUserTags: List<UserTagsModel>?
)