package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.data.TodoDatabase
import com.plcoding.cleanarchitecturenoteapp.data.TodoRepository
import com.plcoding.cleanarchitecturenoteapp.data.TodoRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDataBase(app: Application): TodoDatabase {
        return Room.databaseBuilder(app, TodoDatabase::class.java, "todo_db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImplementation(db.dao)
    }

}