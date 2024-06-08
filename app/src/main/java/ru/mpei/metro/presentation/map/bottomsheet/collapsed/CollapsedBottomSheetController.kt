package ru.mpei.metro.presentation.map.bottomsheet.collapsed

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.R
import ru.mpei.metro.databinding.BottomSheetLayoutBinding
import ru.mpei.metro.domain.graph.MetroGraphProvider
import ru.mpei.metro.presentation.map.MapViewModel
import ru.mpei.metro.presentation.map.bottomsheet.COLLAPSED_HEIGHT
import ru.mpei.metro.presentation.map.bottomsheet.StationDirection
import ru.mpei.metro.presentation.map.bottomsheet.detail.DetailBottomSheetController
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject

private const val COLLAPSED_HEIGHT_WITH_ROUTE_INFO = 330

@MapFragmentScope
class CollapsedBottomSheetController @Inject constructor(
    private val activity: Activity,
    private val mapViewModel: MapViewModel,
    private val metroGraphProvider: MetroGraphProvider,
    private val detailBottomSheetController: DetailBottomSheetController,
    private val suggestedRoutesAdapter: SuggestedRoutesAdapter,
) {
    private val density = activity.resources.displayMetrics.density

    fun initCollapsedBottomSheet(
        lifecycleOwner: LifecycleOwner,
        binding: BottomSheetLayoutBinding,
        bottomSheetBehavior: BottomSheetBehavior<FrameLayout>,
    ) {
        binding.collapsedBottomSheet.fromStation.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.expandedBottomSheet.searchEditText.hint =
                activity.resources.getString(R.string.from)
            mapViewModel.stationDirection = StationDirection.FROM
        }
        binding.collapsedBottomSheet.toStation.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.expandedBottomSheet.searchEditText.hint =
                activity.resources.getString(R.string.to)
            mapViewModel.stationDirection = StationDirection.TO
        }
        binding.collapsedBottomSheet.fromStationClear.setOnClickListener {
            mapViewModel.setSelectedStations(
                fromStation = null,
                toStation = mapViewModel.selectedStations.value?.toStation,
            )
        }
        binding.collapsedBottomSheet.toStationClear.setOnClickListener {
            mapViewModel.setSelectedStations(
                fromStation = mapViewModel.selectedStations.value?.fromStation,
                toStation = null,
            )
        }
        mapViewModel.selectedStations.observe(lifecycleOwner) { selectedStations ->
            binding.collapsedBottomSheet.fromStation.text =
                selectedStations.fromStation?.stationName ?: activity.getString(R.string.from)
            binding.collapsedBottomSheet.toStation.text =
                selectedStations.toStation?.stationName ?: activity.getString(R.string.to)
            if (selectedStations.fromStation != null) {
                binding.collapsedBottomSheet.fromStationClear.visibility = View.VISIBLE
            } else {
                binding.collapsedBottomSheet.fromStationClear.visibility = View.GONE
            }
            if (selectedStations.toStation != null) {
                binding.collapsedBottomSheet.toStationClear.visibility = View.VISIBLE
            } else {
                binding.collapsedBottomSheet.toStationClear.visibility = View.GONE
            }
            if (selectedStations.toStation != null && selectedStations.fromStation != null) {
                mapViewModel.insertHistoryRoute(selectedStations)
                bottomSheetBehavior.peekHeight =
                    (COLLAPSED_HEIGHT_WITH_ROUTE_INFO * density).toInt()
                binding.collapsedBottomSheet.routeSummary.visibility = View.VISIBLE
                binding.collapsedBottomSheet.openDetailButton.visibility = View.VISIBLE
            } else {
                bottomSheetBehavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
                binding.collapsedBottomSheet.routeSummary.visibility = View.GONE
                binding.collapsedBottomSheet.openDetailButton.visibility = View.GONE
            }
            mapViewModel.getRoutes(metroGraphProvider.getMetroGraph(), selectedStations)
        }
        binding.collapsedBottomSheet.swapButton.setOnClickListener {
            mapViewModel.setSelectedStations(
                fromStation = mapViewModel.selectedStations.value?.toStation,
                toStation = mapViewModel.selectedStations.value?.fromStation,
            )
        }
        binding.collapsedBottomSheet.openDetailButton.setOnClickListener {
            detailBottomSheetController.doOnBehaviour {
                it.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        binding.collapsedBottomSheet.suggestedRoutesRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        binding.collapsedBottomSheet.suggestedRoutesRecyclerView.adapter = suggestedRoutesAdapter
        suggestedRoutesAdapter.setOnRouteSelectedListener { route ->
            mapViewModel.setSelectedRoute(route)
        }
        mapViewModel.routes.observe(lifecycleOwner) { routes ->
            if (!routes.isNullOrEmpty()) {
                suggestedRoutesAdapter.resetSelectedRoute()
                suggestedRoutesAdapter.differ.submitList(routes)
                mapViewModel.setSelectedRoute(routes.first())
            }
        }
    }
}
