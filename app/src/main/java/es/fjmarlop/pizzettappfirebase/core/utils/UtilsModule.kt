package es.fjmarlop.pizzettappfirebase.core.utils

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Provides
    fun provideApplicationContext(@ApplicationContext application: Context): Context {
        return application
    }
}