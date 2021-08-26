package ru.alexandro.rijksmuseum.presentation.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import ru.alexandro.rijksmuseum.R
import ru.alexandro.rijksmuseum.base.view.BaseFragment
import ru.alexandro.rijksmuseum.databinding.FragmentArtObjectListBinding
import ru.alexandro.rijksmuseum.presentation.list.adapter.ArtObjectAdapter
import ru.alexandro.rijksmuseum.presentation.list.adapter.VerticalMarginItemDecorator
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState.ArtObjectPage
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListViewState.Loading
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModelImpl


class ArtObjectListFragment :
    BaseFragment<ArtObjectListEvent, ArtObjectListViewModel, FragmentArtObjectListBinding, ArtObjectListViewState>(
        R.layout.fragment_art_object_list
    ) {

    override val viewModel: ArtObjectListViewModel
            by stateViewModel<ArtObjectListViewModelImpl>(state = { requireArguments() })

    override val binding: FragmentArtObjectListBinding
            by viewBinding(FragmentArtObjectListBinding::bind)

    private val artObjectAdapter by lazy {
        ArtObjectAdapter { sendEvent(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.artObjectsRV.apply {

            applyInsetter { type(statusBars = true, navigationBars = true) { padding() } }

            adapter = artObjectAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                VerticalMarginItemDecorator(resources.getDimensionPixelOffset(R.dimen.margin_8dp))
            )
        }
    }


    override fun handleViewState(viewState: ArtObjectListViewState) {
        updateViewsVisibility(viewState)
        when (viewState) {
            Loading -> {
                // nothing to handle here
            }
            is ArtObjectPage -> {
                artObjectAdapter.submitData(viewLifecycleOwner.lifecycle, viewState.data)
            }
        }
    }

    private fun updateViewsVisibility(viewState: ArtObjectListViewState) = with(binding) {
        loadingIndicator.isVisible = viewState == Loading
    }
}