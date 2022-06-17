package com.example.myprojectapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myprojectapp.data.model.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
internal abstract class ArticleDatabase : RoomDatabase() {
    abstract fun personDao(): ArticleDao

}