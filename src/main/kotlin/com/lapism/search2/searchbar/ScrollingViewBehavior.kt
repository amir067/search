package com.lapism.search2.searchbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout


class ScrollingViewBehavior : AppBarLayout.ScrollingViewBehavior {

    constructor()

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            dependency.setBackgroundColor(0)
            dependency.elevation = 0.0f
            dependency.setOutlineProvider(null)
            return true
        }
        return false
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        super.onDependentViewChanged(parent, child, dependency)
        return true
    }

}