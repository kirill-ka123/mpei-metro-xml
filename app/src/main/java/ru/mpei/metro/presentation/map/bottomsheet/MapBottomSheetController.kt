package ru.mpei.metro.presentation.map.bottomsheet

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.R
import ru.mpei.metro.databinding.BottomSheetLayoutBinding
import ru.mpei.metro.presentation.common.FragmentOnCreateViewListener
import ru.mpei.metro.presentation.map.bottomsheet.collapsed.CollapsedBottomSheetController
import ru.mpei.metro.presentation.map.bottomsheet.expanded.ExpandedBottomSheetController
import ru.mpei.metro.presentation.map.di.MapFragmentConstants
import ru.mpei.metro.presentation.map.di.MapFragmentScope
import javax.inject.Inject
import javax.inject.Named

const val COLLAPSED_HEIGHT = 180

@MapFragmentScope
class MapBottomSheetController @Inject constructor(
    private val activity: Activity,
    @Named(MapFragmentConstants.MAP_FRAGMENT_ROOT_VIEW)
    private val rootView: View,
    private val collapsedBottomSheetController: CollapsedBottomSheetController,
    private val expandedBottomSheetController: ExpandedBottomSheetController,
) : FragmentOnCreateViewListener, DefaultLifecycleObserver {
    private var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null
    private var bottomSheetBehaviorCallback: BottomSheetBehaviorCallback? = null

    override fun onCreateView(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(this)
        val binding = BottomSheetLayoutBinding.bind(rootView.findViewById(R.id.bottom_sheet))
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet).apply {
            val density = activity.resources.displayMetrics.density
            peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            state = BottomSheetBehavior.STATE_COLLAPSED
            isHideable = false
            bottomSheetBehaviorCallback = BottomSheetBehaviorCallback(binding).also { callback ->
                addBottomSheetCallback(callback)
            }
        }
        bottomSheetBehavior?.let { bottomSheetBehavior ->
            collapsedBottomSheetController.initCollapsedBottomSheet(
                lifecycleOwner,
                binding,
                bottomSheetBehavior,
            )
            expandedBottomSheetController.initExpandedBottomSheet(
                lifecycleOwner,
                binding,
                bottomSheetBehavior,
            )
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        bottomSheetBehaviorCallback?.let { callback ->
            bottomSheetBehavior?.removeBottomSheetCallback(callback)
        }
        bottomSheetBehaviorCallback = null
        bottomSheetBehavior = null
    }
}
