package com.example.xooicase.di

import com.example.xooicase.data.repository.TestRepositoryImpl
import com.example.xooicase.domain.repository.ITestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideTestRepository() : ITestRepository{
        return TestRepositoryImpl()
    }

}