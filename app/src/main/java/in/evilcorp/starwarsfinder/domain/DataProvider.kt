package `in`.evilcorp.starwarsfinder.domain

import `in`.evilcorp.starwarsfinder.domain.models.CharacterExtraModel
import `in`.evilcorp.starwarsfinder.domain.models.CharacterModel
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable

interface DataProvider {

    fun observeCharacters(): Observable<List<CharacterModel>>

    fun getCharacterExtra(specieId: String): Maybe<CharacterExtraModel>

}