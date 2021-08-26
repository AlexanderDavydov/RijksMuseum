package ru.alexandro.rijksmuseum.router

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alexandro.rijksmuseum.presentation.list.ArtObjectListFragment


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

//    class ArtObjectDetail(argsObject: ArtObjectDetailNavArgs) : Fragments(
//        fragmentCreator = { ArtObjectDetailFragment() },
//        args = argsObject.asArgsBundle()
//    )

}

fun ShareAction(url: String) = ActivityScreen {
    Intent.createChooser(
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post")
            putExtra(Intent.EXTRA_TEXT, url)
        },
        "Share link"
    )
}