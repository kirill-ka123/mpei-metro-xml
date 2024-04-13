package ru.mpei.metro.presentation.map.bottomsheet.expanded

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.databinding.BottomSheetLayoutBinding
import ru.mpei.metro.domain.model.City
import ru.mpei.metro.presentation.map.MapViewModel
import ru.mpei.metro.presentation.map.bottomsheet.StationDirection
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject

@MapFragmentScope
class ExpandedBottomSheetController @Inject constructor(
    private val activity: Activity,
    private val mapViewModel: MapViewModel,
    private val city: City,
    private val suggestedStationAdapter: SuggestedStationAdapter,
    private val historyRouteAdapter: HistoryRouteAdapter,
    private val closeStationAdapter: CloseStationAdapter,
) {
    fun initExpandedBottomSheet(
        lifecycleOwner: LifecycleOwner,
        binding: BottomSheetLayoutBinding,
        bottomSheetBehavior: BottomSheetBehavior<FrameLayout>,
    ) {
        binding.expandedBottomSheet.cancelSearchButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.expandedBottomSheet.searchEditText.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                text?.let { changedText ->
                    mapViewModel.updateSuggestedStationsByQuery(city, changedText.toString())
                }
            }
        )

        val suggestedStationsManager = LinearLayoutManager(activity)
        binding.expandedBottomSheet.suggestedStationsRecyclerView.layoutManager = suggestedStationsManager
        binding.expandedBottomSheet.suggestedStationsRecyclerView.adapter = suggestedStationAdapter
        mapViewModel.suggestedStations.observe(lifecycleOwner) { suggestedStations ->
            suggestedStationAdapter.differ.submitList(suggestedStations)
        }
        suggestedStationAdapter.setOnItemClickListener { station ->
            when (mapViewModel.stationDirection) {
                StationDirection.TO -> mapViewModel.updateSelectedStations(toStation = station)
                StationDirection.FROM -> mapViewModel.updateSelectedStations(fromStation = station)
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val historyManager = LinearLayoutManager(activity)
        binding.expandedBottomSheet.historyRecyclerView.layoutManager = historyManager
        binding.expandedBottomSheet.historyRecyclerView.adapter = historyRouteAdapter
        mapViewModel.historyRoutes.observe(lifecycleOwner) { historyRoutes ->
            binding.expandedBottomSheet.historyLabel.visibility = if (historyRoutes.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            historyRouteAdapter.differ.submitList(historyRoutes)
        }
        historyRouteAdapter.setOnItemClickListener { historyRoute ->
            mapViewModel.updateSelectedStations(
                fromStation = historyRoute.fromStation,
                toStation = historyRoute.toStation,
            )
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val closeStationsManager = LinearLayoutManager(activity)
        binding.expandedBottomSheet.closeStationsRecyclerView.layoutManager = closeStationsManager
        binding.expandedBottomSheet.closeStationsRecyclerView.adapter = closeStationAdapter
        mapViewModel.closeStations.observe(lifecycleOwner) { closeStations ->
            binding.expandedBottomSheet.closeStationsLabel.visibility = if (closeStations.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            closeStationAdapter.differ.submitList(closeStations)
        }
        closeStationAdapter.setOnItemClickListener { station ->
            when (mapViewModel.stationDirection) {
                StationDirection.TO -> mapViewModel.updateSelectedStations(toStation = station)
                StationDirection.FROM -> mapViewModel.updateSelectedStations(fromStation = station)
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}
