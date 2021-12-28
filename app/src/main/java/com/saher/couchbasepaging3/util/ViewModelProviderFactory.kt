package com.saher.couchbasepaging3.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.saher.couchbasepaging3.MyViewModel
import com.saher.couchbasepaging3.repository.PhotoFlowImpl
import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class ViewModelProviderFactory(
    private val repository: PhotoFlowImpl
    ) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MyViewModel::class.java)){
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class")
    }
}