package com.suitmedia.portalsuitmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.suitmedia.portalsuitmedia.data.UserPaging
import com.suitmedia.portalsuitmedia.data.remote.response.DataItem
import com.suitmedia.portalsuitmedia.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import com.suitmedia.portalsuitmedia.Result

class UserRepository(private var apiService: ApiService) {
    fun getUsers(page: Int, perPage: Int): LiveData<Result<PagingData<DataItem>>> = liveData(
        Dispatchers.IO){
        emit(Result.Loading)
        try {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = perPage
                ),
                pagingSourceFactory = {
                    UserPaging(apiService, page)
                }
            ).liveData
            emit(Result.Success(pager))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService).also { instance = it }
            }
    }
}