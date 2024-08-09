package com.suitmedia.portalsuitmedia.data


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.suitmedia.portalsuitmedia.data.remote.response.DataItem
import com.suitmedia.portalsuitmedia.data.remote.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException

class UserPaging(
    private val apiService: ApiService,
    private val page: Int
) : PagingSource<Int, DataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val nextPage = params.key ?: page
            val response = apiService.getUsers(nextPage, params.loadSize)

            LoadResult.Page(
                data = response.data,
                prevKey = if (nextPage == page) null else nextPage - 1,
                nextKey = if (response.data.isEmpty()) null else nextPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition
    }
}