package com.ksuta.finderusertest.network

import com.ksuta.finderusertest.network.model.UserModelDto
import com.ksuta.finderusertest.network.model.UsersModelDto
import com.ksuta.finderusertest.screens.search.UserModel
import com.ksuta.finderusertest.screens.search.UsersModel

internal fun UserModelDto.toUserModel(): UserModel {
    return UserModel(
        userId = userId,
        acceptRate = acceptRate,
        displayName = displayName,
        profileImage = profileImage
    )
}

private fun UsersModelDto.toUsersModel(): UsersModel {
    return UsersModel(itemsUser = itemsUser.map { it.toUserModel() })
}
