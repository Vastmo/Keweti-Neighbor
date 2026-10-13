package dev.vastmo.keweti_neighbor.data.repository

import dev.vastmo.keweti_neighbor.data.source.UserAPI
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.auth.user.UserSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val session: UserSession) : AuthState()
    data class Error(val message: String) : AuthState()
}

class UserRepository(
    private val userAPI: UserAPI,
    private val supabase: SupabaseClient
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _currentUser = MutableStateFlow<UserInfo?>(null)
    val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()

    init {
        // Observing session status on init
        observeSessionChanges()

        loadInitialSession()
    }

    private fun loadInitialSession() {
        scope.launch {
            val currentSession = supabase.auth.currentSessionOrNull()
            _authState.value = if (currentSession != null) {
                AuthState.Success(currentSession)
            } else {
                AuthState.Idle
            }
        }
    }

    private fun observeSessionChanges() {
        scope.launch {
            observeSession().collect { sessionStatus ->
                when (sessionStatus) {
                    is SessionStatus.Authenticated -> {
                        _authState.value = AuthState.Success(sessionStatus.session)
                        _currentUser.value = sessionStatus.session.user
                    }

                    is SessionStatus.NotAuthenticated -> {
                        _authState.value = AuthState.Idle
                        _currentUser.value = null
                    }

                    is SessionStatus.Initializing -> {
                        _authState.value = AuthState.Loading
                        _currentUser.value = null
                    }

                    is SessionStatus.RefreshFailure -> {
                        _authState.value = AuthState.Error("Error refreshing session.")
                    }
                }
            }
        }
    }

    // Sign Up Fun
    // TODO: Need to include extra metadata from user
    // Need phone number, first name, last name
    fun signUp(email: String, password: String) {
        scope.launch {
            try {
                _authState.value = AuthState.Loading
                userAPI.signUp(email, password)
                val session = supabase.auth.currentSessionOrNull()
                if (session != null) {
                    _authState.value = AuthState.Success(session)
                    _currentUser.value = session.user
                } else {
                    _authState.value = AuthState.Error("Sign up succeeded but no session created")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun signIn(email: String, password: String) {
        scope.launch {
            try {
                _authState.value = AuthState.Loading
                userAPI.signIn(email, password)
                val session = supabase.auth.currentSessionOrNull()
                if (session != null) {
                    _authState.value = AuthState.Success(session)
                    _currentUser.value = session.user
                } else {
                    _authState.value =
                        AuthState.Error("Authentication succeeded but no session found")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun changePassword(newPassword: String) {
        scope.launch {
            try {
                userAPI.changePassword(newPassword)
            } catch (e: Exception) {
                // Log error and optionally emit to authState
                _authState.value = AuthState.Error(e.message ?: "Password change failed")
            }
        }
    }

    fun signOut() {
        scope.launch {
            try {
                userAPI.signOut()
                _authState.value = AuthState.Idle
                _currentUser.value = null
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Sign out failed")
            }
        }
    }

    fun getCurrentUser(): UserInfo? = _currentUser.value

    fun observeSession() = supabase.auth.sessionStatus

    fun onDestroy() {
        scope.cancel()
    }
}