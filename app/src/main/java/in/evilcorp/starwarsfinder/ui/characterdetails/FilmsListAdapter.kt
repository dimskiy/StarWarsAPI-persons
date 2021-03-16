package `in`.evilcorp.starwarsfinder.ui.characterdetails

import `in`.evilcorp.starwarsfinder.R
import `in`.evilcorp.starwarsfinder.databinding.LayoutListItemBinding
import `in`.evilcorp.starwarsfinder.ui.FilterableRecycleAdapter
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.FilmViewItem
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class FilmsListAdapter :
    FilterableRecycleAdapter<FilmViewItem, FilmsListAdapter.FilmHolder>() {

    private val clicksSubject = PublishSubject.create<FilmViewItem>()

    fun observeClicks(): Observable<FilmViewItem> = clicksSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutListItemBinding.inflate(inflater, parent, false)
        return FilmHolder(binding)
    }

    inner class FilmHolder(private val viewBinding: LayoutListItemBinding) :
        BindableHolder<FilmViewItem>(viewBinding.root) {

        override fun bind(item: FilmViewItem) {
            val titleDecorated = itemView.context.getString(
                R.string.character_details_film_title,
                item.title,
                item.episodeNum
            )
            viewBinding.textTitle.text = titleDecorated
            itemView.setOnClickListener { clicksSubject.onNext(item) }
        }
    }
}