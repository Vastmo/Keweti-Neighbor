package dev.vastmo.keweti_neighbor

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.vastmo.keweti_neighbor.di.appModule
import dev.vastmo.keweti_neighbor.di.viewModelModule
import dev.vastmo.keweti_neighbor.ui.navigation.NavRoot
import dev.vastmo.keweti_neighbor.ui.theme.AppTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(configuration = koinConfiguration { modules(appModule, viewModelModule) }) {
        AppTheme {
            NavRoot()
        }
    }
}