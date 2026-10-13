package dev.vastmo.keweti_neighbor.di

import dev.vastmo.keweti_neighbor.data.repository.UserRepository
import dev.vastmo.keweti_neighbor.data.source.UserAPI
import dev.vastmo.keweti_neighbor.data.source.UserAPIImpl
import dev.vastmo.keweti_neighbor.ui.screens.settingsScreen.SettingsViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

//val appModule = module {
//    single<SupabaseClient> { create(::buildClient) }
//    single<UserAPI> { UserAPIImpl(get()) }
//    singleOf(::UserRepository)
//}
val appModule = module {
    single<SupabaseClient> { buildClient() }
    single<UserAPI> { UserAPIImpl(get()) }
    single { UserRepository(get(), get()) }
}

val viewModelModule = module {
    viewModelOf(::SettingsViewModel)
}

// TODO: Implement platform-specific secret for keys
private fun buildClient(): SupabaseClient {
    val supabaseClient = createSupabaseClient(
        supabaseUrl = "",
        supabaseKey = ""
    ) {
        install(Auth)
    }
    return supabaseClient
}