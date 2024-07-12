package es.fjmarlop.pizzettappfirebase.core.firebaseModuls

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireBaseFirestoreModule {

    @Provides
    @Singleton
    fun provideFireBaseFirestore() = Firebase.firestore
}