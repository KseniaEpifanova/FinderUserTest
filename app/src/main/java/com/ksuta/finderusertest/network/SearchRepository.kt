package com.ksuta.finderusertest.network

import androidx.paging.PagingSource
import com.ksuta.finderusertest.screens.search.UserModel
import javax.inject.Inject

class SearchRepository
@Inject constructor(
    private val everythingNewsPagingSourceFactory: UsersPagingSource.Factory
) : ISearchRepository{

    override fun queryAll(query: String): PagingSource<Int, UserModel> {
        return everythingNewsPagingSourceFactory.create(query)
    }
}

interface ISearchRepository {
    fun queryAll(query: String): PagingSource<Int, UserModel>
}