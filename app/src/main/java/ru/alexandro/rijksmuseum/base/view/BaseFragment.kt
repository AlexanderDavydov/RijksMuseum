package ru.alexandro.rijksmuseum.base.view

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.alexandro.rijksmuseum.R
import ru.alexandro.rijksmuseum.base.event.BaseEvent
import ru.alexandro.rijksmuseum.base.viewmodel.BaseViewModel
import ru.alexandro.rijksmuseum.extentions.observe
import ru.alexandro.rijksmuseum.extentions.viewScope
import ru.alexandro.rijksmuseum.view.extensions.showClosableSnackbar

/**
 * Abstract Base Fragment implementation encapsulates
 * common event and error handling methods from the view model.
 */
abstract class BaseFragment<E : BaseEvent, VM : BaseViewModel<VS, E>, VB : ViewBinding, VS : BaseViewState>(
    @LayoutRes contentLayoutId: Int = 0
) : Fragment(contentLayoutId), BaseView<E, VM, VB, VS> {

    abstract override val viewModel: VM

    abstract override val binding: VB

    abstract override fun handleViewState(viewState: VS)

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() = with(viewModel) {
        viewState.observe(viewScope, ::handleViewState)
        error.observe(viewScope, ::handleError)
    }

    private fun handleError(error: Throwable) {
        val errorString = error.message ?: getString(R.string.error_unknown)
        binding.root.showClosableSnackbar(errorString)
    }

    protected fun sendEvent(event: E) = viewModel.sendEvent(event)
}