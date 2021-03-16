package `in`.evilcorp.starwarsfinder.ui

import `in`.evilcorp.starwarsfinder.R
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CharacterListDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val paddingHorizontalPx = context.resources.getDimensionPixelSize(R.dimen.screen_padding_horizontal)
    private val paddingTopPx = context.resources.getDimensionPixelSize(R.dimen.screen_padding_top)
    private val paddingBottomPx = context.resources.getDimensionPixelSize(R.dimen.screen_padding_bottom)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = paddingHorizontalPx
        outRect.right = paddingHorizontalPx
        outRect.top = paddingTopPx

        val itemPosition = parent.getChildAdapterPosition(view)
        val totalItems = parent.adapter?.itemCount ?: RecyclerView.NO_POSITION
        outRect.bottom = if (itemPosition == totalItems - 1) paddingBottomPx else 0
    }
}