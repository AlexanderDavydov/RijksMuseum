package ru.alexandro.rijksmuseum.extentions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope


val Fragment.viewScope get() = viewLifecycleOwner.lifecycleScope