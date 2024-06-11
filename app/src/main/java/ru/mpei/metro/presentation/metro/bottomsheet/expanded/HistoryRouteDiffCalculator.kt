package ru.mpei.metro.presentation.metro.bottomsheet.expanded

import androidx.recyclerview.widget.DiffUtil
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.presentation.metro.di.MetroFragmentScope
import javax.inject.Inject

@MetroFragmentScope
class HistoryRouteDiffCalculator @Inject constructor() : DiffUtil.ItemCallback<HistoryRoute>() {
    override fun areItemsTheSame(oldItem: HistoryRoute, newItem: HistoryRoute): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: HistoryRoute, newItem: HistoryRoute): Boolean {
        return oldItem == newItem
    }
}
