package dev.vastmo.keweti_neighbor.domain

// AuthenticationException.kt
class AuthenticationException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    companion object {
        // Pre-defined error messages for common scenarios
        const val ERROR_NETWORK = "Network connection failed"
        const val ERROR_INVALID_CREDENTIALS = "Invalid email or password"
        const val ERROR_EMAIL_EXISTS = "An account with this email already exists"
        const val ERROR_WEAK_PASSWORD = "Password does not meet requirements"
        const val ERROR_USER_DISABLED = "Account has been disabled"
        const val ERROR_SESSION_EXPIRED = "Session has expired, please sign in again"
        const val ERROR_RATE_LIMITED = "Too many attempts, please try again later"
    }
}