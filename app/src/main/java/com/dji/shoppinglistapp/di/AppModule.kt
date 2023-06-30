package com.dji.shoppinglistapp.di

import android.app.Application
import androidx.room.Room
import com.dji.shoppinglistapp.data.local.ListItemDao
import com.dji.shoppinglistapp.data.local.ListItemDatabase
import com.dji.shoppinglistapp.data.repository.ShoppingRepositoryImpl
import com.dji.shoppinglistapp.domain.interactor.ShoppingListInteractor
import com.dji.shoppinglistapp.domain.repository.ShoppingRepository
import com.dji.shoppinglistapp.domain.use_case.InsertItemUseCase
import com.dji.shoppinglistapp.domain.use_case.ShoppingListUseCases
import com.dji.shoppinglistapp.domain.use_case.UpdateItemUseCase
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
    fun provideListItemDatabase(app: Application): ListItemDatabase {
        return Room.databaseBuilder(
            app,
            ListItemDatabase::class.java,
            ListItemDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideShoppingRepository(db: ListItemDatabase): ShoppingRepository {
        return ShoppingRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideShoppingListInteractor(repository: ShoppingRepository): ShoppingListInteractor {
        return ShoppingListInteractor(repository)
    }

    @Provides
    @Singleton
    fun provideShoppingListUseCases(repository: ShoppingRepository): ShoppingListUseCases {
        return ShoppingListUseCases(
            insertItemUseCase = InsertItemUseCase(repository),
            updateItemUseCase = UpdateItemUseCase(repository)
        )
    }
}