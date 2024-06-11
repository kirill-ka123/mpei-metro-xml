package ru.mpei.metro.presentation.map.bottomsheet.collapsed

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.slider.LabelFormatter
import ru.mpei.metro.R
import ru.mpei.metro.databinding.BottomSheetLayoutBinding
import ru.mpei.metro.domain.model.Station
import ru.mpei.metro.domain.route.constructFullTimeOfRoute
import ru.mpei.metro.presentation.map.MetroViewModel
import ru.mpei.metro.presentation.map.bottomsheet.COLLAPSED_HEIGHT
import ru.mpei.metro.presentation.map.bottomsheet.StationDirection
import ru.mpei.metro.presentation.map.bottomsheet.detail.DetailBottomSheetController
import ru.mpei.metro.presentation.map.bottomsheet.expanded.ExpandedBottomSheetController
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import ru.mpei.metro.presentation.map.model.SelectedStations
import javax.inject.Inject

private const val COLLAPSED_HEIGHT_WITH_ROUTE_INFO = 380

@MapFragmentScope
class CollapsedBottomSheetController @Inject constructor(
    private val activity: Activity,
    private val metroViewModel: MetroViewModel,
    private val detailBottomSheetController: DetailBottomSheetController,
    private val expandedBottomSheetController: ExpandedBottomSheetController,
    private val suggestedRoutesAdapter: SuggestedRoutesAdapter,
) {
    private val density = activity.resources.displayMetrics.density

    fun initCollapsedBottomSheet(
        lifecycleOwner: LifecycleOwner,
        binding: BottomSheetLayoutBinding,
        bottomSheetBehavior: BottomSheetBehavior<FrameLayout>,
    ) {
        binding.collapsedBottomSheet.comfortSlider.labelBehavior = LabelFormatter.LABEL_FLOATING
        binding.collapsedBottomSheet.comfortSlider.addOnChangeListener { _, value, fromUser ->
            if (!fromUser) {
                return@addOnChangeListener
            }
            val comfortWeight = value / 10f
            metroViewModel.setSelectedComfortWeight(comfortWeight)
        }
        binding.collapsedBottomSheet.fromStation.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.expandedBottomSheet.searchEditText.hint =
                activity.resources.getString(R.string.from)
            expandedBottomSheetController.stationDirection = StationDirection.FROM
        }
        binding.collapsedBottomSheet.toStation.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.expandedBottomSheet.searchEditText.hint =
                activity.resources.getString(R.string.to)
            expandedBottomSheetController.stationDirection = StationDirection.TO
        }
        binding.collapsedBottomSheet.fromStationClear.setOnClickListener {
            metroViewModel.setSelectedStations(
                fromStation = null,
                toStation = metroViewModel.selectedStations.value?.toStation,
            )
        }
        binding.collapsedBottomSheet.toStationClear.setOnClickListener {
            metroViewModel.setSelectedStations(
                fromStation = metroViewModel.selectedStations.value?.fromStation,
                toStation = null,
            )
        }
        metroViewModel.selectedStations.observe(lifecycleOwner) { selectedStations ->
            binding.handleFromStation(selectedStations.fromStation)
            binding.handleToStation(selectedStations.toStation)
            binding.handleSelectedStations(bottomSheetBehavior, selectedStations)
        }
        binding.collapsedBottomSheet.swapButton.setOnClickListener {
            metroViewModel.setSelectedStations(
                fromStation = metroViewModel.selectedStations.value?.toStation,
                toStation = metroViewModel.selectedStations.value?.fromStation,
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
            metroViewModel.setSelectedRoute(route)
        }
        metroViewModel.routes.observe(lifecycleOwner) { routes ->
            if (!routes.isNullOrEmpty()) {
                suggestedRoutesAdapter.resetSelectedRoute()
                suggestedRoutesAdapter.differ.submitList(routes)
                binding.collapsedBottomSheet.sliderContainer.visibility = View.VISIBLE
            } else {
                suggestedRoutesAdapter.differ.submitList(emptyList())
                binding.collapsedBottomSheet.sliderContainer.visibility = View.GONE
            }
        }
        metroViewModel.selectedRoute.observe(lifecycleOwner) { route ->
            route?.constructFullTimeOfRoute()?.let {
                binding.collapsedBottomSheet.routeSummary.text = it
            }
        }
    }

    private fun BottomSheetLayoutBinding.handleFromStation(fromStation: Station?) {
        if (fromStation != null) {
            collapsedBottomSheet.fromStationClear.visibility = View.VISIBLE
            collapsedBottomSheet.fromStation.text = fromStation.stationName
            collapsedBottomSheet.fromStationIcon.setImageDrawable(
                AppCompatResources.getDrawable(activity, R.drawable.ic_small_circle)
            )
            collapsedBottomSheet.fromStationIcon.setColorFilter(
                Color.parseColor(fromStation.hexColor)
            )
        } else {
            collapsedBottomSheet.fromStationClear.visibility = View.GONE
            collapsedBottomSheet.fromStation.text = activity.getString(R.string.from)
            collapsedBottomSheet.fromStationIcon.setImageDrawable(
                AppCompatResources.getDrawable(activity, R.drawable.ic_medium_circle)
            )
            collapsedBottomSheet.fromStationIcon.clearColorFilter()
        }
    }

    private fun BottomSheetLayoutBinding.handleToStation(toStation: Station?) {
        if (toStation != null) {
            collapsedBottomSheet.toStationClear.visibility = View.VISIBLE
            collapsedBottomSheet.toStation.text = toStation.stationName
            collapsedBottomSheet.toStationIcon.setImageDrawable(
                AppCompatResources.getDrawable(activity, R.drawable.ic_small_circle)
            )
            collapsedBottomSheet.toStationIcon.setColorFilter(
                Color.parseColor(toStation.hexColor)
            )
        } else {
            collapsedBottomSheet.toStationClear.visibility = View.GONE
            collapsedBottomSheet.toStation.text = activity.getString(R.string.to)
            collapsedBottomSheet.toStationIcon.setImageDrawable(
                AppCompatResources.getDrawable(activity, R.drawable.ic_location_pin)
            )
            collapsedBottomSheet.toStationIcon.clearColorFilter()
        }
    }

    private fun BottomSheetLayoutBinding.handleSelectedStations(
        bottomSheetBehavior: BottomSheetBehavior<FrameLayout>,
        selectedStations: SelectedStations,
    ) {
        if (selectedStations.fromStation != null && selectedStations.toStation != null) {
            bottomSheetBehavior.peekHeight =
                (COLLAPSED_HEIGHT_WITH_ROUTE_INFO * density).toInt()
            collapsedBottomSheet.routeSummary.visibility = View.VISIBLE
            collapsedBottomSheet.openDetailButton.visibility = View.VISIBLE
        } else {
            bottomSheetBehavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            collapsedBottomSheet.routeSummary.visibility = View.GONE
            collapsedBottomSheet.openDetailButton.visibility = View.GONE
        }
    }
}
