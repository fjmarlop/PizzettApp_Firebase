package es.fjmarlop.pizzettappfirebase.entidades

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.fjmarlop.pizzettappfirebase.entidades.response.AddressResponse
import es.fjmarlop.pizzettappfirebase.entidades.response.CategoryResponse
import es.fjmarlop.pizzettappfirebase.entidades.response.ClientResponse
import es.fjmarlop.pizzettappfirebase.entidades.response.EmployeeResponse
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EntitiesProvideModules {

    @Provides
    @Singleton
    fun provideCategoryResponse(): Class<CategoryResponse> = CategoryResponse::class.java

    @Provides
    @Singleton
    fun provideClientResponse(): Class<ClientResponse> = ClientResponse::class.java

    @Provides
    @Singleton
    fun provideAddressResponse(): Class<AddressResponse> = AddressResponse::class.java

    @Provides
    @Singleton
    fun provideEmployeeResponse(): Class<EmployeeResponse> = EmployeeResponse::class.java

}
