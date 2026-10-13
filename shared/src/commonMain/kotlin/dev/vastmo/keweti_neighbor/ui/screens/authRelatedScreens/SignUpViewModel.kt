package dev.vastmo.keweti_neighbor.ui.screens.authRelatedScreens

import androidx.lifecycle.ViewModel
import dev.vastmo.keweti_neighbor.data.repository.UserRepository

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun signUp(email: String, password: String) {
        userRepository.signUp(email, password)
    }

    override fun onCleared() {
        super.onCleared()
        userRepository.onDestroy()
    }
}