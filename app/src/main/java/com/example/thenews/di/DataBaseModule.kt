package com.example.thenews.di

import android.content.Context
import androidx.room.Room
import com.example.thenews.data.local.NewDao
import com.example.thenews.data.local.NewDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NewDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewDataBase::class.java, "database"
        ).build()
    }

    @Provides
    fun provideNewsDao(newDataBase: NewDataBase): NewDao {
        return newDataBase.newDao()
    }
}
