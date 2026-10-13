package dev.vastmo.keweti_neighbor.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoutes : NavKey {
    @Serializable
    data object Home : NavRoutes

    @Serializable
    data object Settings : NavRoutes

    @Serializable
    data object Login : NavRoutes
}