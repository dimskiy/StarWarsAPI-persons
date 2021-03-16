package `in`.evilcorp.starwarsfinder.ui.characterdetails.models

import `in`.evilcorp.starwarsfinder.ui.FilterableRecycleAdapter

data class FilmViewItem(
    val id: String,
    val title: String,
    val episodeNum: Int? = null,
    val description: String? = null
) : FilterableRecycleAdapter.Item() {

    override fun isIdEqual(other: FilterableRecycleAdapter.Item): Boolean =
        (other as? FilmViewItem)?.id == id


    override fun isContentEqual(other: FilterableRecycleAdapter.Item): Boolean = other == this
}