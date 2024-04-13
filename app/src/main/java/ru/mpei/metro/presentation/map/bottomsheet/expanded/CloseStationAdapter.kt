package ru.mpei.metro.presentation.map.bottomsheet.expanded

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.mpei.metro.databinding.CloseStationLayoutBinding
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject

@MapFragmentScope
class CloseStationAdapter @Inject constructor(
    closeStationDiffCalculator: CloseStationDiffCalculator
) : RecyclerView.Adapter<CloseStationAdapter.CloseStationViewHolder>() {
    val differ = AsyncListDiffer(this, closeStationDiffCalculator)
    private var itemClickListener: ((Station) -> Unit)? = null

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloseStationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CloseStationLayoutBinding.inflate(inflater, parent, false)

        return CloseStationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CloseStationViewHolder, position: Int) {
        val station = differ.currentList[position]

        with(holder.binding) {
            root.setOnClickListener {
                itemClickListener?.invoke(station)
            }
            val stationColor = Color.parseColor(station.branch.color)
            stationDot.setColorFilter(stationColor)
            stationName.text = station.name
            branchName.text = station.branch.name
        }
    }

    fun setOnItemClickListener(listener: (Station) -> Unit) {
        itemClickListener = listener
    }

    class CloseStationViewHolder(val binding: CloseStationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
