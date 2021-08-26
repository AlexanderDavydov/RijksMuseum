package ru.alexandro.rijksmuseum.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnticipateInterpolator
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import ru.alexandro.rijksmuseum.R
import ru.alexandro.rijksmuseum.base.BaseActivity
import ru.alexandro.rijksmuseum.databinding.ActivityMainBinding
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel.MainEvent
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel.MainViewState
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel.MainViewState.ListScreen
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModel.MainViewState.Splash
import ru.alexandro.rijksmuseum.presentation.viewmodel.MainViewModelImpl

class MainActivity : BaseActivity<MainEvent, MainViewModel, ActivityMainBinding, MainViewState>(
    R.layout.activity_main
) {

    override val viewModel: MainViewModel by stateViewModel<MainViewModelImpl>()

    override val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override val navigator: Navigator = AppNavigator(this, R.id.container)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        sendEvent(MainEvent.ViewReady)
    }

    override fun handleViewState(viewState: MainViewState) {
        when (viewState) {
            is Splash -> {
                with(binding.splashView) {
                    root.apply {
                        isVisible = true
                        root.animate()
                            .setInterpolator(AnticipateInterpolator())
                            .setDuration(1000)
                            .translationY(0f)
                            .setListener(null)
                    }
                    appLogo.animate()
                        .setInterpolator(AnticipateInterpolator())
                        .alpha(1f)
                        .setDuration(300)
                }
            }
            is ListScreen -> with(binding.splashView.root) {
                animate()
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .translationY(-height.toFloat())
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            isVisible = false
                        }
                    })
            }
        }
    }
}