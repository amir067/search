package com.lapism.search2.searchview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.lapism.search.R


class MaterialSearchView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CoordinatorLayout.AttachedBehavior  {

    init {
        View.inflate(context, R.layout.material_search_view, this)

    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return Behavior()
    }

}