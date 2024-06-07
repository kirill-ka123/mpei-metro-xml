package ru.mpei.metro.presentation.map.bottomsheet.detail

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.R
import ru.mpei.metro.common.DiConstants
import ru.mpei.metro.databinding.DetailBottomSheetLayoutBinding
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.map.MapViewModel
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject
import javax.inject.Named

private const val COLLAPSED_HEIGHT = 0

@MapFragmentScope
class DetailBottomSheetController @Inject constructor(
    private val activity: Activity,
    @Named(DiConstants.MAP_FRAGMENT_ROOT_VIEW)
    private val rootView: View,
    private val mapViewModel: MapViewModel,
) : FragmentOnCreateViewListener, DefaultLifecycleObserver {
    private var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null
    private var bottomSheetBehaviorCallback: DetailBottomSheetBehaviorCallback? = null

    override fun onCreateView(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
        val binding = DetailBottomSheetLayoutBinding.bind(rootView.findViewById(R.id.detail_bottom_sheet))
        bottomSheetBehavior = BottomSheetBehavior.from(binding.detailBottomSheet).apply {
            val density = activity.resources.displayMetrics.density
            peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            state = BottomSheetBehavior.STATE_COLLAPSED
            isHideable = false
            bottomSheetBehaviorCallback =
                DetailBottomSheetBehaviorCallback(binding).also { callback ->
                    addBottomSheetCallback(callback)
                }
        }

        mapViewModel.route.observe(lifecycleOwner) { route ->
            binding.detailBottomSheetContent.routeInfoView.setRoute(route)
        }

        mapViewModel.selectedStations.observe(lifecycleOwner) { selectedStations ->
            selectedStations.fromStation?.let { fromStation ->
                binding.detailBottomSheetContent.routeCardLayout.fromStation.text = fromStation.stationName
                binding.detailBottomSheetContent.routeCardLayout.fromStationIcon.setColorFilter(
                    Color.parseColor(fromStation.hexColor)
                )
            }
            selectedStations.toStation?.let { toStation ->
                binding.detailBottomSheetContent.routeCardLayout.toStation.text = toStation.stationName
                binding.detailBottomSheetContent.routeCardLayout.fromStationIcon.setColorFilter(
                    Color.parseColor(toStation.hexColor)
                )
            }
        }
    }

    fun doOnBehaviour(block: (behaviour: BottomSheetBehavior<FrameLayout>) -> Unit) =
        bottomSheetBehavior?.let {
            block.invoke(it)
        }

    override fun onDestroy(owner: LifecycleOwner) {
        bottomSheetBehaviorCallback?.let { callback ->
            bottomSheetBehavior?.removeBottomSheetCallback(callback)
        }
        bottomSheetBehaviorCallback = null
        bottomSheetBehavior = null
    }
}