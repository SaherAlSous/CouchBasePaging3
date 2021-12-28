package com.saher.couchbasepaging3.adapter

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saher.couchbasepaging3.db.CouchBase
import com.saher.couchbasepaging3.db.Photo
import java.lang.Exception

class PhotoPaging(context: Context): PagingSource<Int, Photo>() {
    /**
     * get the source of data from couchbase...
     * you can define the size of data each time you make a request.
     */
    private val photoList = CouchBase(context).getPhotoListFromDb()

    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1

        return try {
            return LoadResult.Page(
                data = photoList,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (page == 2) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}