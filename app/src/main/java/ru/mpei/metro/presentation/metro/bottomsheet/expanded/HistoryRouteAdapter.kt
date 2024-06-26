package ru.mpei.metro.presentation.metro.bottomsheet.expanded

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.mpei.metro.databinding.HistoryRouteLayoutBinding
import ru.mpei.metro.domain.model.HistoryRoute
import ru.mpei.metro.presentation.metro.di.MetroFragmentScope
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@MetroFragmentScope
class HistoryRouteAdapter @Inject constructor(
    historyRouteDiffCalculator: HistoryRouteDiffCalculator
) : RecyclerView.Adapter<HistoryRouteAdapter.HistoryRouteViewHolder>() {
    val differ = AsyncListDiffer(this, historyRouteDiffCalculator)
    private var itemClickListener: ((HistoryRoute) -> Unit)? = null

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryRouteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HistoryRouteLayoutBinding.inflate(inflater, parent, false)

        return HistoryRouteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryRouteViewHolder, position: Int) {
        val historyRoute = differ.currentList[position]

        with(holder.binding) {
            root.setOnClickListener {
                itemClickListener?.invoke(historyRoute)
            }
            val fromStationColor = Color.parseColor(historyRoute.fromStation.hexColor)
            fromStationDot.setColorFilter(fromStationColor)
            fromStationName.text = historyRoute.fromStation.stationName

            val toStationColor = Color.parseColor(historyRoute.toStation.hexColor)
            toStationDot.setColorFilter(toStationColor)
            toStationName.text = historyRoute.toStation.stationName

            val pattern = "d MMM | H:mm"
            val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
            descriptionDate.text = historyRoute.date.format(formatter)
        }
    }

    fun setOnItemClickListener(listener: (HistoryRoute) -> Unit) {
        itemClickListener = listener
    }

    class HistoryRouteViewHolder(val binding: HistoryRouteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
