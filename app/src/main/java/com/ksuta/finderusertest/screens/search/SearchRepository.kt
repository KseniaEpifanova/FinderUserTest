package com.ksuta.finderusertest.screens.search

import androidx.paging.PagingSource
import com.ksuta.finderusertest.network.UsersPagingSource
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