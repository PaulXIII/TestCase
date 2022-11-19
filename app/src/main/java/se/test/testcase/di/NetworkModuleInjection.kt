package se.test.testcase.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.test.testcase.data.repositories.api.ApiInterface

@InstallIn(SingletonComponent::class)
@Module
class NetworkModuleInjection {

    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    companion object {
        private const val BASE_URL = "https://restaurant-code-test.herokuapp.com/"
    }
}