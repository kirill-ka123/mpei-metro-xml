package ru.mpei.metro.presentation.metro.bottomsheet.expanded

import androidx.recyclerview.widget.DiffUtil
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.presentation.metro.di.MetroFragmentScope
import javax.inject.Inject

@MetroFragmentScope
class SuggestedStationDiffCalculator @Inject constructor() : DiffUtil.ItemCallback<Station>() {
    override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
        return oldItem == newItem
    }
}
