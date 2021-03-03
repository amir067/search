package com.lapism.search2.widget

import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


// open class Behavior<S : MaterialSearchView> : CoordinatorLayout.Behavior<S>()
@Suppress("unused")
class Behavior : CoordinatorLayout.Behavior<MaterialSearchView>() {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: MaterialSearchView,
        dependency: View
    ): Boolean {
        return if (dependency is AppBarLayout) {
            true
        } else
            if (dependency is LinearLayout || dependency is BottomNavigationView) {
                dependency.z = child.z + 1
                true
            } else {
                super.layoutDependsOn(parent, child, dependency)
            }
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: MaterialSearchView,
        dependency: View
    ): Boolean {
        if (dependency is AppBarLayout) {
            child.translationY = dependency.getY()
            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: MaterialSearchView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

}