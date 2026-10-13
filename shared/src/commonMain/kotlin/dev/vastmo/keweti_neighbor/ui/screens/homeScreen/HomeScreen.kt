package dev.vastmo.keweti_neighbor.ui.screens.homeScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Composable
fun HomeScreen(onSettingsClick: () -> Unit) {
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keweti") }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    TooltipBox(
                        positionProvider =
                            TooltipDefaults.rememberTooltipPositionProvider(
                                TooltipAnchorPosition.Above
                            ),
                        tooltip = {
                            PlainTooltip(
                                modifier =
                                    Modifier.semantics {
                                        // TODO(b/496338253): Remove this modifier once bug where
                                        //   tooltip text is not announced by a11y screen readers
                                        //   is resolved.
                                        liveRegion = LiveRegionMode.Assertive
                                        paneTitle = "Go to settings"
                                    }
                            ) {
                                Text("Go to settings")
                            }
                        },
                        state = rememberTooltipState(),
                    ) {
                        IconButton(onClick = {
                            onSettingsClick()
                        }) {
                            Icon(
                                tint = MaterialTheme.colorScheme.onPrimary,
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Go to settings",
                            )
                        }
                    }
                }
            )
        }
    ) {


    }
}

suspend fun createUser() {
    val supabaseClient = createSupabaseClient(
        supabaseUrl = "https://sjtczkslykfvvvhmrzor.supabase.co",
        supabaseKey = "sb_publishable_6bkuTKM5zqSd95GTrfTXNw_gyPkMg-E"
    ) {
        install(Auth)
    }

    val u = supabaseClient.auth.signUpWith(Email) {
        email = "pubsecs@proton.me"
        password = "example-password"
        data = buildJsonObject {
//            put("first_name", )
            put("user_type", "landlord")
        }
    }
}

