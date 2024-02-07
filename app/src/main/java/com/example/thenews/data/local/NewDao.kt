package com.example.thenews.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thenews.model.entity.NewEntity

@Dao
interface NewDao {
    @Query("SELECT * FROM newentity")
    suspend fun getAll(): List<NewEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg news: NewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(news: NewEntity)

    @Query("SELECT * FROM newentity WHERE title LIKE :title LIMIT 1")
    suspend fun findByTitle(title: String): NewEntity

    @Query("SELECT * FROM newentity WHERE isFavourite = 1")
    suspend fun getFavourites(): List<NewEntity>

    @Delete
    suspend fun deleteFavNew(favNewEntity: NewEntity)
}
