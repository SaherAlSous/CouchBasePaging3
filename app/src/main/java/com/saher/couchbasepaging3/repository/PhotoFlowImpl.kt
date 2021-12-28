package com.saher.couchbasepaging3.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.saher.couchbasepaging3.adapter.PhotoPaging
import com.saher.couchbasepaging3.db.Photo
import kotlinx.coroutines.flow.Flow

/**
 * I converted the Paging data into a flow so
 * that it can update the adapter each time you need more data.
 */

class PhotoFlowImpl(
    private val photos: PhotoPaging
): PhotoFlow {
    override fun fetchPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            defaultPagingConfig(),
            pagingSourceFactory = {photos}
        ).flow
    }

    /**
     * you can make the same transferred data size over here as in couchbase if you want.
     */
    private fun defaultPagingConfig() : PagingConfig{
        return PagingConfig(
            pageSize = 100,
            prefetchDistance = 100,
            enablePlaceholders = false,
            initialLoadSize = 100,
            maxSize = 300
        )
    }
}