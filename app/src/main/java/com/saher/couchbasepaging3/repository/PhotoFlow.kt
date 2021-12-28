package com.saher.couchbasepaging3.repository

import androidx.paging.PagingData
import com.saher.couchbasepaging3.db.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoFlow {
    fun fetchPhotos(): Flow<PagingData<Photo>>
}