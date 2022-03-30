package com.ksuta.finderusertest.screens.details

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksuta.finderusertest.screens.search.UserModel
import com.ksuta.finderusertest.screens.search.UserTagsModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailUserViewModel
@Inject constructor(
    private val model: UserModel?,
    private val repo: IDetailRepository
) : ViewModel() {

    val setModel = MutableLiveData<DetailModel>()

    init {
        model?.userId?.let{
            getUsersTags(it)
        }
    }

    private fun getUsersTags(id: Int) {
        viewModelScope.launch {
            runCatching { repo.getUserTags(id) }
                .onSuccess {
                    map(model,it)
                }
                .onFailure (Timber::e)
        }
    }

    private fun map(model: UserModel?, tags: List<UserTagsModel?>?) {
        setModel.value = DetailModel(
            model?.displayName.orEmpty(),
            model?.profileImage.orEmpty(),
            model?.reputation,
            tags?.joinToString(separator = SEPARATOR){ it?.name.orEmpty() },
            model?.location,
            getDate(model?.creationDate)
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(l: Long?): String = l?.let { Date(it) }?.let {
        SimpleDateFormat(DATA_PATTERN).format(it)
    }?: ""

    companion object{
        private const val SEPARATOR = ","
        private const val DATA_PATTERN = "MM dd, yyyy hh:mm"

    }
}
