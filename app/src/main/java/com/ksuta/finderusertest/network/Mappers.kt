package com.ksuta.finderusertest.network

import com.ksuta.finderusertest.network.model.UserModelDto
import com.ksuta.finderusertest.network.model.UserModelTagsDto
import com.ksuta.finderusertest.screens.search.UserModel
import com.ksuta.finderusertest.screens.search.UserTagsModel

internal fun UserModelDto.toUserModel(): UserModel {
    return UserModel(
        userId = userId,
        acceptRate = acceptRate,
        displayName = displayName,
        profileImage = profileImage,
        reputation = reputation,
        location = location,
        creationDate = creationDate
    )
}

internal fun UserModelTagsDto.toUserTagsModel(): UserTagsModel {
    return UserTagsModel(
        name = name
    )
}
