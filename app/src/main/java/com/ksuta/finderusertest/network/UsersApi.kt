package com.ksuta.finderusertest.network

import com.ksuta.finderusertest.network.model.UsersModelDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
        @Query("sort") sort: String,
        @Query("site") site: String
    ): Response<UsersModelDto>

    @GET("users")
    suspend fun getUsersWithQuery(
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
        @Query("sort") sort: String,
        @Query("inname") inname: String,
        @Query("site") site: String
    ): Response<UsersModelDto>


    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}
