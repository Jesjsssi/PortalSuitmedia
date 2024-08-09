package com.suitmedia.portalsuitmedia.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suitmedia.portalsuitmedia.di.Injection
import com.suitmedia.portalsuitmedia.repository.UserRepository
import com.suitmedia.portalsuitmedia.ui.thirdscreen.ThirdViewModel

class ViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ThirdViewModel::class.java) -> {
                return ThirdViewModel(userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}