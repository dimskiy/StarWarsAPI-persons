package `in`.evilcorp.starwarsfinder.ui.characterlist.models

import `in`.evilcorp.starwarsfinder.ui.FilterableRecycleAdapter

data class CharacterListViewItem(
    val id: String,
    val name: String,
    val birthYear: String?,
    val height: Double?
) : FilterableRecycleAdapter.Item() {

    override fun isIdEqual(other: FilterableRecycleAdapter.Item): Boolean =
        (other as? CharacterListViewItem)?.id == id

    override fun isContentEqual(other: FilterableRecycleAdapter.Item): Boolean =
        (other as? CharacterListViewItem) == this

    override fun isMatchFilter(query: CharSequence?): Boolean =
        query?.let {
            name.contains(it, ignoreCase = true)
        } ?: true
}