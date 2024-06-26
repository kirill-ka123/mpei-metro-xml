package ru.mpei.metro.presentation.metro.bottomsheet.expanded

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import ru.mpei.metro.databinding.SuggestedStationLayoutBinding
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.presentation.metro.di.MetroFragmentScope
import javax.inject.Inject

@MetroFragmentScope
class SuggestedStationAdapter @Inject constructor(
    suggestedStationDiffCalculator: SuggestedStationDiffCalculator,
) : RecyclerView.Adapter<SuggestedStationAdapter.SuggestedStationViewHolder>() {
    val differ = AsyncListDiffer(this, suggestedStationDiffCalculator)
    private var itemClickListener: ((Station) -> Unit)? = null

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedStationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SuggestedStationLayoutBinding.inflate(inflater, parent, false)

        return SuggestedStationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestedStationViewHolder, position: Int) {
        val station = differ.currentList[position]

        with(holder.binding) {
            root.setOnClickListener {
                itemClickListener?.invoke(station)
            }
            val stationColor = Color.parseColor(station.hexColor)
            stationDot.setColorFilter(stationColor)
            stationName.text = station.stationName
            branchName.text = station.branchName
        }
    }

    fun setOnItemClickListener(listener: (Station) -> Unit) {
        itemClickListener = listener
    }

    class SuggestedStationViewHolder(val binding: SuggestedStationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
