package com.ksuta.finderusertest.network

import com.ksuta.finderusertest.network.model.UserModelDto
import com.ksuta.finderusertest.network.model.UsersModelDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("users/{ids}")
    fun getUser(
        @Path("ids") ids: Int,
        //@Query("inname") inname: String,
        @Query("site") site: String
    ): Call<UserModelDto>

    companion object {

        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}
