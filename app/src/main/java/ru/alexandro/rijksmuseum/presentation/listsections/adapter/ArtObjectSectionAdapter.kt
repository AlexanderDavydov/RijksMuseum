package ru.alexandro.rijksmuseum.presentation.listsections.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.rijksmuseum.databinding.ItemArtObjectBinding
import ru.alexandro.rijksmuseum.databinding.ItemArtObjectHeaderBinding
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent.ArtObjectClick
import ru.alexandro.rijksmuseum.presentation.listsections.viewmodel.ArtObjectSectionListViewModel.ArtObjectSectionListEvent.ShareArtObject

class ArtObjectSectionAdapter(
    private val action: (ArtObjectSectionListEvent) -> Unit
) : PagingDataAdapter<ArtObjectItem, ArtObjectSectionAdapter.BaseArtObjectItemViewHolder>(
    ArtObjectItemComparator
) {

    companion object {
        const val HeaderType = 0
        const val ItemType = 1
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ArtObjectItem.ArtObjectItemHeader -> HeaderType
            is ArtObjectItem.ArtObjectItemData -> ItemType
            null -> throw IllegalStateException("Item must not be null")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseArtObjectItemViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding = when (viewType) {
            HeaderType -> ItemArtObjectHeaderBinding.inflate(inflater, parent, false)
            ItemType -> ItemArtObjectBinding.inflate(inflater, parent, false)
            else -> throw IllegalStateException("Unknown item to bind")
        }

        return BaseArtObjectItemViewHolder(binding, action)
    }

    override fun onBindViewHolder(holder: BaseArtObjectItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class BaseArtObjectItemViewHolder(
        private val binding: ViewBinding,
        private val action: (ArtObjectSectionListEvent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArtObjectItem) {
            when (item) {
                is ArtObjectItem.ArtObjectItemHeader -> {
                    bindHeader(binding as ItemArtObjectHeaderBinding, item.name)
                }
                is ArtObjectItem.ArtObjectItemData -> {
                    bindItem(binding as ItemArtObjectBinding, item.artObject)
                }
            }
        }

        private fun bindHeader(binding: ItemArtObjectHeaderBinding, name: String) {
            binding.root.text = name
        }

        private fun bindItem(binding: ItemArtObjectBinding, item: ArtObject) = with(binding) {

            root.setOnClickListener { action(ArtObjectClick(item.objectNumber)) }

            content.apply {

                Glide.with(root)
                    .load(item.imageUrl)
                    .centerCrop()
                    .into(imageView)

                name.text = item.title
                shareButton.setOnClickListener { action(ShareArtObject(item.webLink)) }
            }
        }
    }


    object ArtObjectItemComparator : DiffUtil.ItemCallback<ArtObjectItem>() {
        override fun areItemsTheSame(oldItem: ArtObjectItem, newItem: ArtObjectItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtObjectItem, newItem: ArtObjectItem) =
            oldItem == newItem
    }
}

sealed class ArtObjectItem {
    data class ArtObjectItemHeader(val name: String) : ArtObjectItem()
    data class ArtObjectItemData(val artObject: ArtObject) : ArtObjectItem()
}