package ru.mpei.metro.presentation.map.bottomsheet.detail

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.databinding.DetailBottomSheetLayoutBinding

class DetailBottomSheetBehaviorCallback(
    private val binding: DetailBottomSheetLayoutBinding,
) : BottomSheetBehavior.BottomSheetCallback() {
    override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                binding.detailBottomSheetContent.root.visibility = View.VISIBLE
            }

            else -> Unit
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) = with(binding) {
        if (slideOffset > 0) {
            detailBottomSheetContent.root.alpha = 2 * slideOffset
        }
    }
}