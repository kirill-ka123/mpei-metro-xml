package ru.mpei.metro.presentation.common

import androidx.lifecycle.LifecycleOwner

interface FragmentOnCreateViewListener {
    fun onCreateView(lifecycleOwner: LifecycleOwner)
}
