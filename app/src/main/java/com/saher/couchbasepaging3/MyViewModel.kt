package com.saher.couchbasepaging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.saher.couchbasepaging3.db.Photo
import com.saher.couchbasepaging3.repository.PhotoFlowImpl
import kotlinx.coroutines.flow.Flow

class MyViewModel(
    private val photoFlow: PhotoFlowImpl
): ViewModel() {

    fun getContentPaging(): Flow<PagingData<Photo>> = photoFlow.fetchPhotos().cachedIn(viewModelScope)
}