package gg.onlineja.onlinecom.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import gg.onlineja.onlinecom.data.repository.FinProductsRepository
import org.koin.dsl.module

var databaseModule = module {
    fun provideDatastore(context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    single {
        FinProductsRepository(get())
    }

    single {
        provideDatastore(get())
    }
}
val Context.dataStore by preferencesDataStore(name = "settings")