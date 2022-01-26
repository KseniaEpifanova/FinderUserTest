package com.ksuta.finderusertest.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DetailUserViewModel
@Inject constructor(
    private val ids: Int,
    private val repo: IDetailRepository
) : ViewModel() {

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            runCatching { repo.getUser(ids) }
                .onSuccess{
                    Timber.i("onSuccess Timber" +it)
                    Log.i("onSuccess ", it.toString()) }
                .onFailure {
                    Timber.e("onFailure Timber"+it)
                    Log.e("onFailure ",it.localizedMessage,it)
                }
        }
    }

}
