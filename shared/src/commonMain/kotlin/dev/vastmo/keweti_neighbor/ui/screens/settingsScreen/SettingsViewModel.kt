package dev.vastmo.keweti_neighbor.ui.screens.settingsScreen

import androidx.lifecycle.ViewModel
import dev.vastmo.keweti_neighbor.data.repository.AuthState
import dev.vastmo.keweti_neighbor.data.repository.UserRepository
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userState: StateFlow<AuthState> = userRepository.authState
    val currentUser: StateFlow<UserInfo?> = userRepository.currentUser

    fun signOut() {
        userRepository.signOut()
    }

    fun changePassword(newPassword: String) {
        userRepository.changePassword(newPassword)
    }

    override fun onCleared() {
        super.onCleared()
        userRepository.onDestroy()
    }
}