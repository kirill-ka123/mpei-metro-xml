package ru.mpei.metro.presentation.map.bottomsheet.expanded

import androidx.recyclerview.widget.DiffUtil
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject

@MapFragmentScope
class HistoryRouteDiffCalculator @Inject constructor() : DiffUtil.ItemCallback<HistoryRoute>() {
    override fun areItemsTheSame(oldItem: HistoryRoute, newItem: HistoryRoute): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: HistoryRoute, newItem: HistoryRoute): Boolean {
        return oldItem == newItem
    }
}
