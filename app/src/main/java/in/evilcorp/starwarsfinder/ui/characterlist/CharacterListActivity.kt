package `in`.evilcorp.starwarsfinder.ui.characterlist

import `in`.evilcorp.starwarsfinder.R
import `in`.evilcorp.starwarsfinder.databinding.ActivityListBinding
import `in`.evilcorp.starwarsfinder.helpers.RxDisposable
import `in`.evilcorp.starwarsfinder.helpers.RxDisposableDelegate
import `in`.evilcorp.starwarsfinder.platform.getAppComponent
import `in`.evilcorp.starwarsfinder.ui.CharacterListDecorator
import `in`.evilcorp.starwarsfinder.ui.ViewModelFactory
import `in`.evilcorp.starwarsfinder.ui.characterdetails.CharacterDetailsActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import com.jakewharton.rxbinding4.swiperefreshlayout.refreshes
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import timber.log.Timber

class CharacterListActivity : AppCompatActivity(), RxDisposable by RxDisposableDelegate() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewBinding: ActivityListBinding

    private val characterAdapter: CharacterListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CharacterListAdapter()
    }
    private val viewModel: CharacterListViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory).get(CharacterListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)

        viewBinding = ActivityListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewBinding.listCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(CharacterListDecorator(context))
        }
    }

    override fun onStart() {
        super.onStart()

        bindCommands()
        bindControls()
    }

    private fun bindCommands() {
        viewModel.observeCharacters()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(characterAdapter::setNewItems) { Timber.e(it) }
            .untilClear()

        viewModel.observeSearchQueries()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(characterAdapter::filter) { Timber.e(it) }
            .untilClear()

        viewModel.observeLoadingVisible()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewBinding.refreshLayout::setRefreshing) { Timber.e(it) }
            .untilClear()

        viewModel.observeLoadedCount()
            .observeOn(AndroidSchedulers.mainThread())
            .map { counter -> getString(R.string.label_persons_found, counter).orEmpty()
            }
            .subscribe(viewBinding.labelCount::setText) { Timber.e(it) }
            .untilClear()

        viewModel.observeOpenDetails()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { CharacterDetailsActivity.show(context = this, characterId = it.id) },
                { Timber.e(it) }
            )
            .untilClear()
    }

    private fun bindControls() {
        viewBinding.searchPanel.queryTextChanges()
            .flatMapCompletable(viewModel::onSearchQueryChanged)
            .subscribe({}, { Timber.e(it) })
            .untilClear()

        viewBinding.refreshLayout.refreshes()
            .flatMapCompletable { viewModel.onRefreshList() }
            .subscribe({}, { Timber.e(it) })
            .untilClear()

        characterAdapter.observeClicks()
            .flatMapCompletable(viewModel::onCharacterSelected)
            .subscribe({}, { Timber.e(it) })
            .untilClear()
    }

    override fun onStop() {
        super.onStop()
        clearAll()
    }
}