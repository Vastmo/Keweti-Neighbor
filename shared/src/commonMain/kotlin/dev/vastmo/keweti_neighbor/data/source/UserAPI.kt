package dev.vastmo.keweti_neighbor.data.source

import dev.vastmo.keweti_neighbor.domain.AuthenticationException
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

interface UserAPI {
    suspend fun signUp(email: String, password: String)
    suspend fun signIn(email: String, password: String)
    suspend fun changePassword(newPassword: String)
    suspend fun signOut()
    suspend fun resetPassword(email: String)
}

// UserAPIImpl.kt
class UserAPIImpl(private val supabase: SupabaseClient) : UserAPI {

    override suspend fun signUp(email: String, password: String) {
        try {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        } catch (e: Exception) {
            // Handle registration errors
            throw AuthenticationException("Sign up failed: ${e.message}", e)
        }
    }

    override suspend fun signIn(email: String, password: String) {
        try {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        } catch (e: Exception) {
            throw AuthenticationException("Sign in failed: ${e.message}", e)
        }
    }

    override suspend fun changePassword(newPassword: String) {
        try {
            // Requires user to be signed in first
            val session = supabase.auth.currentSessionOrNull()
            if (session == null) {
                throw AuthenticationException("No active session found")
            }

            supabase.auth.updateUser {
                this.password = newPassword
            }
        } catch (e: Exception) {
            throw AuthenticationException("Password change failed: ${e.message}", e)
        }
    }

    override suspend fun signOut() {
        try {
            supabase.auth.signOut()
        } catch (e: Exception) {
            throw AuthenticationException("Sign out failed: ${e.message}", e)
        }
    }

    override suspend fun resetPassword(email: String) {
        try {
            supabase.auth.resetPasswordForEmail(email)
        } catch (e: Exception) {
            throw AuthenticationException("Password reset failed: ${e.message}", e)
        }
    }
}
