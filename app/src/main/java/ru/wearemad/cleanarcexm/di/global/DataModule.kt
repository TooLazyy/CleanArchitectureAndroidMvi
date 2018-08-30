package ru.wearemad.cleanarcexm.di.global

import android.app.Application
import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.data.ApiMock
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.data.database.db.AppDatabase
import ru.wearemad.cleanarcexm.data.database.db.DB_NAME
import ru.wearemad.cleanarcexm.data.database.mappers.ContactDetailsMapper
import ru.wearemad.cleanarcexm.data.database.mappers.ContactMapper
import ru.wearemad.cleanarcexm.data.repositories.ContactDetailsRepositoryImpl
import ru.wearemad.cleanarcexm.data.repositories.ContactsListRepositoryImpl
import ru.wearemad.cleanarcexm.data.repositories.ContactsSearchRepositoryImpl
import ru.wearemad.cleanarcexm.data.repositories.FavoritesRepositoryImpl
import ru.wearemad.cleanarcexm.domain.commands.GetContactDetailsCommand
import ru.wearemad.cleanarcexm.domain.commands.GetContactsListCommand
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactDetailsRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideApi(): ContactsApi = ApiMock()

    @Provides
    @Singleton
    fun provideContactMapper(): ContactMapper = ContactMapper()

    @Provides
    @Singleton
    fun provideContactDetailsMapper(): ContactDetailsMapper = ContactDetailsMapper()

    @Provides
    @Singleton
    fun provideDataBase(context: Application): AppDatabase = Room
            .databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideContactsListRepository(
            db: AppDatabase,
            cmd: GetContactsListCommand,
            mapper: ContactMapper):
            ContactListRepository =
            ContactsListRepositoryImpl(db, cmd, mapper)

    @Provides
    fun provideContactDetailsRepository(
            db: AppDatabase,
            cmd: GetContactDetailsCommand,
            mapper: ContactDetailsMapper):
            ContactDetailsRepository =
            ContactDetailsRepositoryImpl(db, cmd, mapper)

    @Provides
    fun provideContactsSearchRepository():
            ContactsSearchRepository = ContactsSearchRepositoryImpl()

    @Provides
    fun provideFavoritesRepository(db: AppDatabase, mapper: ContactMapper):
            FavoritesRepository = FavoritesRepositoryImpl(db, mapper)
}