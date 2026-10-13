package dev.vastmo.keweti_neighbor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import dev.vastmo.keweti_neighbor.ui.screens.homeScreen.HomeScreen
import dev.vastmo.keweti_neighbor.ui.screens.settingsScreen.SettingsScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavRoot() {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(NavRoutes.Home::class, NavRoutes.Home.serializer())
                    subclass(NavRoutes.Settings::class, NavRoutes.Settings.serializer())
                }
            }
        },
        NavRoutes.Home
    )

    NavDisplay(
        backStack = backStack, entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ), entryProvider = { key ->
            when (key) {
                is NavRoutes.Home -> {
                    NavEntry(key) {
                        HomeScreen(onSettingsClick = {
                            backStack.add(NavRoutes.Settings)
                        })
                    }
                }

                is NavRoutes.Settings -> {
                    NavEntry(key) {
                        SettingsScreen(onBack = { backStack.remove(key) })
                    }
                }

                else -> error("Unknown navkey $key")
            }

        })
}