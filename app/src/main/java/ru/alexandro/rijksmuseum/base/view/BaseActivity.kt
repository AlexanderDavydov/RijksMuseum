package ru.alexandro.rijksmuseum.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.inject
import ru.alexandro.rijksmuseum.R
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel
import ru.alexandro.rijksmuseum.extentions.observe
import ru.alexandro.rijksmuseum.view.extensions.showClosableSnackbar

/**
 * Abstract Base Activity implementation encapsulates
 * common event and error handling methods from the view model.
 */
abstract class BaseActivity<E : BaseEvent, VM : BaseViewModel<VS, E>, VB : ViewBinding, VS : BaseViewState>(
    @LayoutRes contentLayoutId: Int
) : AppCompatActivity(contentLayoutId), BaseView<E, VM, VB, VS> {

    abstract override val viewModel: VM

    abstract override val binding: VB

    abstract override fun handleViewState(viewState: VS)

    private val navigatorHolder: NavigatorHolder by inject()

    abstract val navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.observe(lifecycleScope) { handleViewState(it) }
        viewModel.error.observe(lifecycleScope) { handleError(it) }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    private fun handleError(error: Throwable) {
        val errorString = error.message ?: getString(R.string.error_unknown)
        binding.root.showClosableSnackbar(errorString)
    }

    fun sendEvent(event: E) = viewModel.sendEvent(event)
}