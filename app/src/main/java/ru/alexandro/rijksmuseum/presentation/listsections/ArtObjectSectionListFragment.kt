package ru.alexandro.rijksmuseum.presentation.listsections

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
import ru.alexandro.rijksmuseum.presentation.listsections.adapter.ArtObjectSectionAdapter
import ru.alexandro.rijksmuseum.presentation.listsections.adapter.VerticalMarginItemDecorator
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState.ArtObjectPage
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListViewState.Loading
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModelImpl


class ArtObjectSectionListFragment :
    BaseFragment<ArtObjectSectionListViewModel.ArtObjectSectionListEvent, ArtObjectSectionListViewModel, FragmentArtObjectListBinding, ArtObjectSectionListViewState>(
        R.layout.fragment_art_object_list
    ) {

    override val viewModel: ArtObjectSectionListViewModel
            by stateViewModel<ArtObjectSectionListViewModelImpl>(state = { requireArguments() })

    override val binding: FragmentArtObjectListBinding
            by viewBinding(FragmentArtObjectListBinding::bind)

    private val artObjectAdapter by lazy {
        ArtObjectSectionAdapter { sendEvent(it) }
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


    override fun handleViewState(viewState: ArtObjectSectionListViewState) {
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

    private fun updateViewsVisibility(viewState: ArtObjectSectionListViewState) = with(binding) {
        loadingIndicator.isVisible = viewState == Loading
    }
}