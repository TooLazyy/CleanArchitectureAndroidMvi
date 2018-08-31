package ru.wearemad.cleanarcexm.di.favorites

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.favorites.FavoritesInteractor
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository

@Module
class FavoritesModule {

    @Provides
    fun provideFavoritesInteractor(favsRep: FavoritesRepository)
            = FavoritesInteractor(favsRep)
}