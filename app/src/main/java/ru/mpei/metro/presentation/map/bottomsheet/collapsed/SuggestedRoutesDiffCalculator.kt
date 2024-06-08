package ru.mpei.metro.presentation.map.bottomsheet.collapsed

import androidx.recyclerview.widget.DiffUtil
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject

@MapFragmentScope
class SuggestedRoutesDiffCalculator @Inject constructor() : DiffUtil.ItemCallback<Route>() {
    override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
        return oldItem.routeNodes == newItem.routeNodes
    }

    override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
        return oldItem == newItem
    }
}
