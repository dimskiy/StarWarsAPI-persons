package `in`.evilcorp.starwarsfinder.ui.characterdetails

import `in`.evilcorp.starwarsfinder.R
import `in`.evilcorp.starwarsfinder.databinding.ActivityDetailsBinding
import `in`.evilcorp.starwarsfinder.helpers.RxDisposable
import `in`.evilcorp.starwarsfinder.helpers.RxDisposableDelegate
import `in`.evilcorp.starwarsfinder.platform.getAppComponent
import `in`.evilcorp.starwarsfinder.ui.CharacterListDecorator
import `in`.evilcorp.starwarsfinder.ui.ViewModelFactory
import `in`.evilcorp.starwarsfinder.ui.characterdetails.models.CharacterDetailsViewItem
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import timber.log.Timber

class CharacterDetailsActivity : AppCompatActivity(), RxDisposable by RxDisposableDelegate() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewBinding: ActivityDetailsBinding

    private val viewModel: CharacterDetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, viewModelFactory).get(CharacterDetailsViewModel::class.java)
    }
    private val filmAdapter: FilmsListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        FilmsListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        getAppComponent().inject(this)
        super.onCreate(savedInstanceState)

        viewBinding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewBinding.listFilms.apply {
            adapter = filmAdapter
            layoutManager = object  : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean = false
            }
            addItemDecoration(CharacterListDecorator(context))
        }
    }

    override fun onStart() {
        super.onStart()

        bindCommands()
        bindControls()
    }

    private fun bindCommands() {
        viewModel.getCharacterDetails(getCharacterId())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setView) { Timber.e(it) }
            .untilClear()

        viewModel.observeDescriptionShow()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { showAlertDialog(it.title, it.description.orEmpty()) },
                { Timber.e(it) }
            )
            .untilClear()
    }

    private fun getCharacterId(): String = intent.getStringExtra(CHARACTER_ID_KEY)
        ?: throw IllegalStateException("No character in fragment bundle")

    private fun setView(viewItem: CharacterDetailsViewItem) {
        with(viewBinding) {
            progressView.isVisible = false

            textName.setText(viewItem.name)
            textName.inputType = InputType.TYPE_NULL
            layoutTextName.isVisible = viewItem.name.isNotEmpty() == true

            textBirth.setText(viewItem.birthYear)
            textBirth.inputType = InputType.TYPE_NULL
            layoutTextBirth.isVisible = viewItem.birthYear?.isNotEmpty() == true

            textHeight.setText(
                getString(
                    R.string.character_details_text_height,
                    viewItem.heightCm,
                    viewItem.heightInch
                )
            )
            textHeight.inputType = InputType.TYPE_NULL
            layoutTextHeight.isVisible = viewItem.heightCm != null

            textRace.setText(viewItem.raceName)
            textRace.inputType = InputType.TYPE_NULL
            layoutTextRace.isVisible = viewItem.raceName?.isNotEmpty() == true

            textLanguage.setText(viewItem.language)
            textLanguage.inputType = InputType.TYPE_NULL
            layoutTextLanguage.isVisible = viewItem.language?.isNotEmpty() == true

            textHomeworld.setText(viewItem.homeworld)
            textHomeworld.inputType = InputType.TYPE_NULL
            layoutTextHomeworld.isVisible = viewItem.homeworld?.isNotEmpty() == true

            textPopulation.setText(viewItem.population?.toString())
            textPopulation.inputType = InputType.TYPE_NULL
            layoutTextPopulation.isVisible = viewItem.population != null

            filmAdapter.setNewItems(viewItem.films)
            groupFilms.isVisible = viewItem.films.isNotEmpty()
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setCancelable(true)
            .show()
    }

    private fun bindControls() {
        filmAdapter.observeClicks()
            .flatMapCompletable(viewModel::onFilmSelected)
            .subscribe({}, { Timber.e(it) })
            .untilClear()
    }

    override fun onStop() {
        super.onStop()
        clearAll()
    }

    companion object {
        fun show(context: Context, characterId: String) {
            val intent = Intent(context, CharacterDetailsActivity::class.java).apply {
                putExtra(CHARACTER_ID_KEY, characterId)
            }
            context.startActivity(intent)
        }

        private const val CHARACTER_ID_KEY = "CHARACTER_ID_KEY"
    }
}