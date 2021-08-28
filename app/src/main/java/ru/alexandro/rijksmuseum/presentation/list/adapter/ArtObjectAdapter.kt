package ru.alexandro.rijksmuseum.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.alexandro.domain.model.ArtObject
import ru.alexandro.rijksmuseum.databinding.ItemArtObjectBinding
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent.ArtObjectClick
import ru.alexandro.rijksmuseum.presentation.list.viewmodel.ArtObjectListViewModel.ArtObjectListEvent.ShareArtObject

class ArtObjectAdapter(
    private val action: (ArtObjectListEvent) -> Unit
) : PagingDataAdapter<ArtObject, ArtObjectAdapter.ArtObjectViewHolder>(ArtObjectComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtObjectViewHolder(
            ItemArtObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            action
        )

    override fun onBindViewHolder(holder: ArtObjectViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ArtObjectViewHolder(
        private val binding: ItemArtObjectBinding,
        private val action: (ArtObjectListEvent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArtObject) = with(binding) {

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

    object ArtObjectComparator : DiffUtil.ItemCallback<ArtObject>() {
        override fun areItemsTheSame(oldItem: ArtObject, newItem: ArtObject) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ArtObject, newItem: ArtObject) =
            oldItem == newItem
    }
}