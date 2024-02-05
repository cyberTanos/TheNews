package com.example.thenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thenews.model.entity.NewEntity

@Database(entities = [NewEntity::class], version = 1)
abstract class NewDataBase : RoomDatabase() {

    abstract fun newDao(): NewDao
}
