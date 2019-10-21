package io.gripxtech.odoojsonrpcclient.core.utils.recycler.decorators

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class HorizontalLinearItemDecorator(
    private val horizontalSpaceHeight: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) =
        run {
            if (parent.getChildAdapterPosition(view)
                != parent.adapter?.itemCount ?: 1 - 1
            ) {
                outRect.right = horizontalSpaceHeight
            }
        }
}