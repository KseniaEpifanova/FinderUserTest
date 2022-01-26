package com.ksuta.finderusertest.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ksuta.finderusertest.network.UsersApi.Companion.SITE_KEY
import com.ksuta.finderusertest.network.model.UsersModelDto
import com.ksuta.finderusertest.screens.search.UserModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

class UsersPagingSource @AssistedInject constructor(
    private val newsService: UsersApi,
    @Assisted("query") private val query: String
) : PagingSource<Int, UserModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {

        return try {
            val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(UsersApi.MAX_PAGE_SIZE)
            val responses =
                if (query.isBlank()) getAllUsers(pageNumber, pageSize) else
                    getUsersByQuery(
                        pageNumber,
                        pageSize,
                        query
                    )
            getUsersPage(responses, pageNumber)
        } catch (e: HttpException) {
            Timber.e(e.message())
            LoadResult.Error(e)
        } catch (e: Exception) {
            Timber.e(e.message.toString())
            LoadResult.Error(e)
        }
    }

    private suspend fun getAllUsers(pageNumber: Int, pageSize: Int) =
        newsService.getUsers(pageNumber, pageSize, SORT_KEY, SITE_KEY)


    private suspend fun getUsersByQuery(pageNumber: Int, pageSize: Int, query: String) =
        newsService.getUsersWithQuery(pageNumber, pageSize, SORT_KEY, query, SITE_KEY)


    private fun getUsersPage(
        response: Response<UsersModelDto>,
        pageNumber: Int
    ): LoadResult<Int, UserModel> {
        return if (response.isSuccessful) {
            val users = response.body()!!.itemsUser.map { it.toUserModel() }

            val nextPageNumber = if (users.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(users, prevPageNumber, nextPageNumber)
        } else {
            Timber.e(response.toString())
            LoadResult.Error(HttpException(response))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("query") query: String): UsersPagingSource
    }

    companion object {

        const val INITIAL_PAGE_NUMBER = 1
        const val SORT_KEY = "name"
    }
}