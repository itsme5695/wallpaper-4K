package com.example.newhdwallpaper.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newhdwallpaper.models.Hit
import com.example.newhdwallpaper.models.Photo
import com.example.newhdwallpaper.retrofit.ApiService
import retrofit2.Call
import java.lang.Exception
import javax.security.auth.callback.Callback

class PhotoSource(var apiService: ApiService, var category: String) : PagingSource<Int, Hit>() {
    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return state.anchorPosition?.let {
            var page = state.closestPageToPosition(it)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        try {
            var pageNumber = params.key ?: 1
            var photos = apiService.getUsers(pageNumber, category)
            if (pageNumber > 1) {
                return LoadResult.Page(photos.hits, pageNumber - 1, pageNumber + 1)
            } else {
                return LoadResult.Page(photos.hits, null, pageNumber + 1)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }
}