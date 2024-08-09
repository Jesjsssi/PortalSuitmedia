package com.suitmedia.portalsuitmedia.di

import com.suitmedia.portalsuitmedia.data.remote.retrofit.ApiConfig
import com.suitmedia.portalsuitmedia.repository.UserRepository

object Injection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}