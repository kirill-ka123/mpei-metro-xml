package ru.mpei.metro.presentation.map.bottomsheet.collapsed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.mpei.metro.R
import ru.mpei.metro.databinding.SuggestedRoutesLayoutBinding
import ru.mpei.metro.domain.model.Route
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@MapFragmentScope
class SuggestedRoutesAdapter @Inject constructor(
    private val context: Context,
    suggestedRoutesDiffCalculator: SuggestedRoutesDiffCalculator,
) : RecyclerView.Adapter<SuggestedRoutesAdapter.SuggestedRouteViewHolder>() {
    val differ = AsyncListDiffer(this, suggestedRoutesDiffCalculator)
    private var routeSelectedListener: ((Route) -> Unit)? = null
    private var selectedRoutePosition = 0

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedRouteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SuggestedRoutesLayoutBinding.inflate(inflater, parent, false)

        return SuggestedRouteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestedRouteViewHolder, position: Int) {
        val route = differ.currentList[position]

        with(holder.binding) {
            root.setOnClickListener {
                updateSelectedRoutePosition(position = holder.adapterPosition)
                routeSelectedListener?.invoke(route)
            }
            val routeTimeInMinutes = TimeUnit.SECONDS.toMinutes(route.totalTime.toLong())
            val routeTimeText = context.getString(R.string.minutes, routeTimeInMinutes.toString())
            routeTime.text = routeTimeText

            val routeComfortText = context.getString(
                R.string.comfort,
                DecimalFormat("0.0").format(route.totalComfort * 10)
            )
            routeComfort.text = routeComfortText

            if (position == selectedRoutePosition) {
                root.setBackgroundResource(R.drawable.selected_rounded_corner_background)
            } else {
                root.setBackgroundResource(R.drawable.rounded_corner_background)
            }
        }
    }

    fun setOnRouteSelectedListener(listener: (Route) -> Unit) {
        routeSelectedListener = listener
    }

    fun resetSelectedRoute() {
        selectedRoutePosition = 0
    }

    private fun updateSelectedRoutePosition(position: Int) {
        val prevSelectedPosition = selectedRoutePosition
        selectedRoutePosition = position
        notifyItemChanged(prevSelectedPosition)
        notifyItemChanged(position)

    }

    class SuggestedRouteViewHolder(
        val binding: SuggestedRoutesLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}
