package com.ksuta.finderusertest.screens.search

data class UserModel(
    val acceptRate: String?,
    val displayName: String?,
    val profileImage: String?
)

data class UsersModel(
    val itemsUser: List<UserModel>?
)