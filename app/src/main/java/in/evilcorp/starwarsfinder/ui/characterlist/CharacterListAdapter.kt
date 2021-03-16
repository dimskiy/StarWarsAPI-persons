package `in`.evilcorp.starwarsfinder.ui.characterlist

import `in`.evilcorp.starwarsfinder.databinding.LayoutListItemBinding
import `in`.evilcorp.starwarsfinder.ui.FilterableRecycleAdapter
import `in`.evilcorp.starwarsfinder.ui.characterlist.models.CharacterListViewItem
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class CharacterListAdapter :
    FilterableRecycleAdapter<CharacterListViewItem, CharacterListAdapter.CharacterHolder>() {

    private val clicksSubject = PublishSubject.create<CharacterListViewItem>()

    fun observeClicks(): Observable<CharacterListViewItem> = clicksSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutListItemBinding.inflate(inflater, parent, false)
        return CharacterHolder(binding)
    }

    inner class CharacterHolder(private val viewBinding: LayoutListItemBinding) :
        BindableHolder<CharacterListViewItem>(viewBinding.root) {

        override fun bind(item: CharacterListViewItem) {
            viewBinding.textTitle.text = item.name
            itemView.setOnClickListener { clicksSubject.onNext(item) }
        }
    }
}