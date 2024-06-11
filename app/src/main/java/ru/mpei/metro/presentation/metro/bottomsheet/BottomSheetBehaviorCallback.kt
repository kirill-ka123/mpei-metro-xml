package ru.mpei.metro.presentation.metro.bottomsheet

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.mpei.metro.databinding.BottomSheetLayoutBinding
import ru.mpei.metro.presentation.common.hideKeyboard
import ru.mpei.metro.presentation.common.showKeyboard

class BottomSheetBehaviorCallback(
    private val binding: BottomSheetLayoutBinding,
) : BottomSheetBehavior.BottomSheetCallback() {
    override fun onStateChanged(bottomSheet: View, newState: Int) = with(binding) {
        when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                expandedBottomSheet.searchEditText.showKeyboard()

                expandedBottomSheet.root.alpha = 1f
                collapsedBottomSheet.root.visibility = View.INVISIBLE
                expandedBottomSheet.root.visibility = View.VISIBLE
            }

            BottomSheetBehavior.STATE_COLLAPSED -> {
                expandedBottomSheet.searchEditText.hideKeyboard()
                expandedBottomSheet.searchEditText.text = null

                collapsedBottomSheet.root.alpha = 1f
                collapsedBottomSheet.root.visibility = View.VISIBLE
                expandedBottomSheet.root.visibility = View.INVISIBLE
            }

            else -> Unit
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) = with(binding) {
        if (slideOffset > 0) {
            collapsedBottomSheet.root.alpha = 1 - 2 * slideOffset
            expandedBottomSheet.root.alpha = 2 * slideOffset

            if (slideOffset > 0.5) {
                collapsedBottomSheet.root.visibility = View.INVISIBLE
                expandedBottomSheet.root.visibility = View.VISIBLE
            }

            if (slideOffset < 0.5) {
                collapsedBottomSheet.root.visibility = View.VISIBLE
                expandedBottomSheet.root.visibility = View.INVISIBLE
            }
        }
    }
}