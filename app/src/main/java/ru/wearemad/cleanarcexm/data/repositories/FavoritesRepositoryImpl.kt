package ru.wearemad.cleanarcexm.data.repositories

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.data.database.db.AppDatabase
import ru.wearemad.cleanarcexm.data.database.entity.FavoritesEntity
import ru.wearemad.cleanarcexm.data.database.mappers.ContactMapper
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository
import javax.inject.Inject

@Screen
class FavoritesRepositoryImpl
@Inject constructor(
        private val appDatabase: AppDatabase,
        private val contactMapper: ContactMapper
) : FavoritesRepository {

    override fun updateContactFavorite(id: Long, isFavorite: Boolean): Observable<Unit> {
        return Observable.fromCallable {
            appDatabase.favoritesDao().getFavorites()
        }
                .subscribeOn(Schedulers.io())
                .map {
                    val item = if (it.isEmpty()) {
                        FavoritesEntity(mutableListOf())
                    } else {
                        it[0]
                    }
                    if (isFavorite) {
                        item.favorites.add(id)
                    } else {
                        item.favorites.remove(id)
                    }
                    item
                }
                .map {
                    appDatabase.favoritesDao().updateFavorites(it)
                }
    }

    override fun getFavoritesList(): Observable<HashSet<Long>> {
        return Observable.fromCallable {
            appDatabase.favoritesDao().getFavorites()
        }
                .subscribeOn(Schedulers.io())
                .map {
                    val item = if (it.isEmpty()) {
                        FavoritesEntity(mutableListOf())
                    } else {
                        it[0]
                    }
                    item.favorites.toHashSet()
                }
    }

    override fun getFavoriteUsers(): Observable<List<Contact>> {
        return Observable.combineLatest(
                Observable.fromCallable {
                    appDatabase.favoritesDao().getFavorites()
                }
                        .map {
                            val item = if (it.isEmpty()) {
                                FavoritesEntity(mutableListOf())
                            } else {
                                it[0]
                            }
                            item.favorites.toHashSet()
                        },
                Observable.fromCallable {
                    appDatabase.userContactsDao()
                            .getContactsList()
                            .map { contactMapper.fromEntity(it) }
                },
                BiFunction<HashSet<Long>, List<Contact>, List<Contact>> { favorites, contacts ->
                    contacts.filter {
                        favorites.contains(it.id)
                    }
                }

        )
                .subscribeOn(Schedulers.computation())
    }
}