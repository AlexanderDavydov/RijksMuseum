package ru.alexandro.rijksmuseum.router

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alexandro.rijksmuseum.presentation.detail.ArtObjectDetailFragment
import ru.alexandro.rijksmuseum.presentation.list.ArtObjectListFragment
import ru.alexandro.rijksmuseum.presentation.listsections.ArtObjectSectionListFragment


sealed class Fragments(
    private val args: Bundle = Bundle.EMPTY,
    override val clearContainer: Boolean = true,
    private val fragmentCreator: Creator<FragmentFactory, Fragment>
) : FragmentScreen {


    override fun createFragment(factory: FragmentFactory): Fragment =
        fragmentCreator.create(factory).apply { arguments = args }

    object ArtObjectList : Fragments(
        fragmentCreator = { ArtObjectListFragment() }
    )

    object ArtObjectSectionList : Fragments(
        fragmentCreator = { ArtObjectSectionListFragment() }
    )

    class ArtObjectDetail(objectNumber: String) : Fragments(
        fragmentCreator = { ArtObjectDetailFragment() },
        args = bundleOf(ARG_ART_OBJECT_NUMBER to objectNumber)
    ) {
        companion object {
            const val ARG_ART_OBJECT_NUMBER = "arg_art_object_number"
        }
    }
}

fun ShareAction(url: String) = ActivityScreen {
    Intent.createChooser(
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        },
        "Share link"
    )
}