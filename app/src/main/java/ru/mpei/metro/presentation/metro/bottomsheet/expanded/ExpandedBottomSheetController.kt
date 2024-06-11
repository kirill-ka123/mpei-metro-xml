package ru.mpei.metro.presentation.metro.bottomsheet.expanded

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.databinding.BottomSheetLayoutBinding
import ru.mpei.metro.presentation.metro.MetroViewModel
import ru.mpei.metro.presentation.metro.bottomsheet.StationDirection
import ru.mpei.metro.presentation.metro.di.MetroFragmentScope
import javax.inject.Inject

@MetroFragmentScope
class ExpandedBottomSheetController @Inject constructor(
    private val activity: Activity,
    private val metroViewModel: MetroViewModel,
    private val suggestedStationAdapter: SuggestedStationAdapter,
    private val historyRouteAdapter: HistoryRouteAdapter,
    private val closeStationAdapter: CloseStationAdapter,
) {
    var stationDirection = StationDirection.FROM

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
                    metroViewModel.updateSuggestedStationsByQuery(changedText.toString())
                }
            }
        )

        val suggestedStationsManager = LinearLayoutManager(activity)
        binding.expandedBottomSheet.suggestedStationsRecyclerView.layoutManager =
            suggestedStationsManager
        binding.expandedBottomSheet.suggestedStationsRecyclerView.adapter = suggestedStationAdapter
        metroViewModel.suggestedStations.observe(lifecycleOwner) { suggestedStations ->
            suggestedStationAdapter.differ.submitList(suggestedStations)
        }
        suggestedStationAdapter.setOnItemClickListener { station ->
            when (stationDirection) {
                StationDirection.TO -> metroViewModel.updateSelectedStations(toStation = station)
                StationDirection.FROM -> metroViewModel.updateSelectedStations(fromStation = station)
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val historyManager = LinearLayoutManager(activity)
        binding.expandedBottomSheet.historyRecyclerView.layoutManager = historyManager
        binding.expandedBottomSheet.historyRecyclerView.adapter = historyRouteAdapter
        metroViewModel.historyRoutes.observe(lifecycleOwner) { historyRoutes ->
            binding.expandedBottomSheet.historyLabel.visibility = if (historyRoutes.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            historyRouteAdapter.differ.submitList(historyRoutes)
        }
        historyRouteAdapter.setOnItemClickListener { historyRoute ->
            metroViewModel.updateSelectedStations(
                fromStation = historyRoute.fromStation,
                toStation = historyRoute.toStation,
            )
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val closeStationsManager = LinearLayoutManager(activity)
        binding.expandedBottomSheet.closeStationsRecyclerView.layoutManager = closeStationsManager
        binding.expandedBottomSheet.closeStationsRecyclerView.adapter = closeStationAdapter
        metroViewModel.closeStations.observe(lifecycleOwner) { closeStations ->
            binding.expandedBottomSheet.closeStationsLabel.visibility =
                if (closeStations.isEmpty()) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            closeStationAdapter.differ.submitList(closeStations)
        }
        closeStationAdapter.setOnItemClickListener { station ->
            when (stationDirection) {
                StationDirection.TO -> metroViewModel.updateSelectedStations(toStation = station)
                StationDirection.FROM -> metroViewModel.updateSelectedStations(fromStation = station)
            }
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}
