package ru.alexandro.rijksmuseum.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import ru.alexandro.domain.model.ArtObjectDetail
import ru.alexandro.rijksmuseum.R
import ru.alexandro.rijksmuseum.base.view.BaseFragment
import ru.alexandro.rijksmuseum.databinding.FragmentArtObjectDetailBinding
import ru.alexandro.rijksmuseum.databinding.LayoutArtInfoBinding
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailViewState
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModel.ArtDetailViewState.Loading
import ru.alexandro.rijksmuseum.presentation.detail.viewmodel.ArtObjectDetailViewModelImpl

class ArtObjectDetailFragment :
    BaseFragment<ArtObjectDetailViewModel.ArtDetailEvent, ArtObjectDetailViewModel, FragmentArtObjectDetailBinding, ArtDetailViewState>(
        R.layout.fragment_art_object_detail
    ) {

    override val viewModel: ArtObjectDetailViewModel
            by stateViewModel<ArtObjectDetailViewModelImpl>(state = { requireArguments() })

    override val binding: FragmentArtObjectDetailBinding
            by viewBinding(FragmentArtObjectDetailBinding::bind)

    private val infoBottomSheet by lazy { BottomSheetBehavior.from(binding.infoBottomSheetView) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoBottomSheet.apply {
            isDraggable = true
            isHideable = true
            state = STATE_HIDDEN
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        STATE_HIDDEN -> binding.openInfoButton.show()
                        STATE_EXPANDED -> binding.openInfoButton.hide()
                        else -> {
                            // no-op
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }

        binding.infoBottomSheetView.applyInsetter { type(navigationBars = true) { padding() } }

        binding.openInfoButton.apply {
            setOnClickListener {
                infoBottomSheet.state = STATE_EXPANDED
            }

            applyInsetter { type(navigationBars = true) { margin() } }
        }
    }

    override fun handleViewState(viewState: ArtDetailViewState) {
        updateViewsVisibility(viewState)
        when (viewState) {
            is Loading -> {
                // nothing to handle here
            }
            is ArtDetailViewState.ArtObjectData -> {
                with(binding) {
                    Glide.with(requireContext())
                        .load(viewState.data.imageUrl)
                        .into(imageView)

                    setupDetailViews(infoBottomSheetContent, viewState.data)
                }
            }
        }
    }

    private fun setupDetailViews(
        infoBottomSheet: LayoutArtInfoBinding,
        objectDetail: ArtObjectDetail
    ) = with(infoBottomSheet) {
        title.text = objectDetail.title
        artist.text = objectDetail.artists.joinToString(separator = ", ")
        description.text = objectDetail.description
    }

    private fun updateViewsVisibility(viewState: ArtDetailViewState) = with(binding) {
        loadingIndicator.isVisible = viewState == Loading
    }
}