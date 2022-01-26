package com.ksuta.finderusertest.screens.details

import com.ksuta.finderusertest.network.UsersApi
import com.ksuta.finderusertest.network.UsersApi.Companion.SITE_KEY
import com.ksuta.finderusertest.network.UsersApi.Companion.SORT_KEY
import com.ksuta.finderusertest.network.toUserModel
import com.ksuta.finderusertest.network.toUserTagsModel
import com.ksuta.finderusertest.screens.search.UserModel
import com.ksuta.finderusertest.screens.search.UserTagsModel
import retrofit2.await
import javax.inject.Inject

class DetailRepository
@Inject constructor(
    private val api: UsersApi
) : IDetailRepository {

    override suspend fun getUser(ids: Int): UserModel? =
        api.getUser(ids, SITE_KEY).await().itemsUser.firstOrNull()?.toUserModel()

    override suspend fun getUserTags(ids: Int) =
        api.getUserTags(ids, SORT_KEY, SITE_KEY).await().itemsTags.take(3).map {
            it.toUserTagsModel()
        }
}

interface IDetailRepository {
    suspend fun getUser(ids: Int): UserModel?
    suspend fun getUserTags(ids: Int): List<UserTagsModel?>
}