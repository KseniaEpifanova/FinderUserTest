package com.ksuta.finderusertest.screens.details

import com.ksuta.finderusertest.network.UsersApi
import com.ksuta.finderusertest.network.UsersPagingSource.Companion.SITE_KEY
import com.ksuta.finderusertest.network.model.UserModelDto
import retrofit2.await
import javax.inject.Inject

class DetailRepository
   @Inject constructor(
       private val api: UsersApi ): IDetailRepository{

    override suspend fun getUser(ids: Int) = api.getUser(ids, SITE_KEY).await()
}

interface IDetailRepository{
    suspend fun getUser(ids: Int): UserModelDto
}