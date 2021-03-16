package `in`.evilcorp.starwarsfinder.ui

import `in`.evilcorp.starwarsfinder.ui.FilterableRecycleAdapter.BindableHolder
import `in`.evilcorp.starwarsfinder.ui.FilterableRecycleAdapter.Item
import android.view.View
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class FilterableRecycleAdapter<T : Item, H : BindableHolder<T>> : ListAdapter<T, H>(DiffCallback<T>()) {

    private val nonFilteredList = mutableListOf<Item>()
    private var lastFilterQuery: CharSequence = ""
    private val filterInstance = ListFilter()

    @Deprecated("Use 'addNewItems(list)' to maintain filterable", replaceWith = ReplaceWith("addNewItems(list)"))
    override fun submitList(list: MutableList<T>?) {
        super.submitList(list)
    }

    @Deprecated("Use 'addNewItems(list)' to maintain filterable", replaceWith = ReplaceWith("addNewItems(list)"))
    override fun submitList(list: MutableList<T>?, commitCallback: Runnable?) {
        super.submitList(list, commitCallback)
    }

    override fun onBindViewHolder(holder: H, position: Int) = holder.bind(getItem(position))

    fun setNewItems(list: List<T>) {
        nonFilteredList.apply {
            clear()
            addAll(list)
        }
        filter()
    }

    fun filter(query: CharSequence? = null) {
        val newQuery = query ?: lastFilterQuery
        lastFilterQuery = newQuery
        filterInstance.filter(newQuery)
    }

    abstract class BindableHolder<T : Item>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    abstract class Item {
        abstract fun isIdEqual(other: Item): Boolean
        abstract fun isContentEqual(other: Item): Boolean
        open fun isMatchFilter(query: CharSequence?): Boolean = true
    }

    private class DiffCallback<T: Item> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isIdEqual(newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.isContentEqual(newItem)
    }

    private inner class ListFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredItems = nonFilteredList
                .filter { it.isMatchFilter(constraint) }
                .toMutableList()

            return FilterResults().apply { values = filteredItems }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val newItems = results?.values as? MutableList<T>
            submitList(newItems)
        }
    }
}