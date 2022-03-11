package com.app.ravn_challenge.di

import com.app.ravn_challenge.repository.PeopleRepository
import com.app.ravn_challenge.repository.PeopleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(peopleRepositoryImpl: PeopleRepositoryImpl): PeopleRepository
}