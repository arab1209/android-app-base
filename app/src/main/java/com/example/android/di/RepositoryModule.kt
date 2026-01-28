package com.example.android.di

import com.example.android.data.repository.ExampleRepositoryImpl
import com.example.android.domain.repository.ExampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExampleRepository(
        impl: ExampleRepositoryImpl
    ): ExampleRepository
}