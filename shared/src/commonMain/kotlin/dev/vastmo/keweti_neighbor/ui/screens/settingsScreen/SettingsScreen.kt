package dev.vastmo.keweti_neighbor.ui.screens.settingsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            MediumFlexibleTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimary,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
//                actions = {
//                    if (currentUser?.id == null) {
//                        LoggedOutActions()
//                    } else {
//                        LoggedInActions()
//                    }
//                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().padding(5.dp),
        ) {
            if (currentUser?.id == null) {
                SegmentedListItem(
                    enabled = false,
                    shapes = ListItemDefaults.segmentedShapes(
                        index = 0,
                        count = 1,
                    ),
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = null,
                        )
                    },
                    content = {
                        Text("Hello, Guest")
                    },
                    onClick = {},
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            // TODO: Open account creation.
                        },
                    ) {
                        Text("Create an account")
                    }

                    TextButton(
                        onClick = {
                            // TODO: Open login.
                        },
                    ) {
                        Text("Login to your account")
                    }
                }
            } else {
                Text("Hello, ${currentUser?.id}")

                Row {
                    TextButton(
                        onClick = {
                            // TODO: Open profile.
                        },
                    ) {
                        Text("Open profile")
                    }

                    TextButton(
                        onClick = {
                            // TODO: Delete profile.
                        },
                    ) {
                        Text("Change password")
                    }

                    TextButton(
                        onClick = {
                            // TODO: Delete profile.
                        },
                    ) {
                        Text("Delete profile")
                    }
                }
            }
        }
    }
}

@Composable
private fun LoggedOutActions() {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            TooltipAnchorPosition.Above,
        ),
        tooltip = {
            PlainTooltip(
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Assertive
                    paneTitle = "Login"
                },
            ) {
                Text("Login")
            }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = {
                // TODO: Open login.
            },
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.AutoMirrored.Filled.Login,
                contentDescription = "Login",
            )
        }
    }
}

@Composable
private fun LoggedInActions() {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            TooltipAnchorPosition.Above,
        ),
        tooltip = {
            PlainTooltip(
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Assertive
                    paneTitle = "Sign Out"
                },
            ) {
                Text("Sign Out")
            }
        },
        state = rememberTooltipState(),
    ) {
        IconButton(
            onClick = {
                // TODO: Sign out.
            },
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Sign Out",
            )
        }
    }
}
