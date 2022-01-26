package com.ksuta.finderusertest.utils

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

fun Activity.findNavController(@IdRes viewId: Int): NavController =
    Navigation.findNavController(this, viewId)

fun Fragment.navigate(bundle: NavDirections) {
    NavHostFragment.findNavController(this).navigate(bundle)
}

inline fun <T> LiveData<T>.observeToSafe(lcOwner: LifecycleOwner,
                                       crossinline onHandleContent: (T) -> Unit) {
    observe(lcOwner, Observer { it?.let(onHandleContent) })
}
