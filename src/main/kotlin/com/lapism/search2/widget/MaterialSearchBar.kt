package com.lapism.search2.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.lapism.search.R


@Suppress("unused")
class MaterialSearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var card: MaterialCardView? = null
    private var toolbar: MaterialSearchToolbar? = null

    init {
        View.inflate(context, R.layout.material_search_bar, this)

        card = findViewById(R.id.search_bar_card)
        toolbar = findViewById(R.id.search_bar_toolbar)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.MaterialSearchBar, defStyleAttr, defStyleRes
        )

        val defaultMarginsStartEnd =
            context.resources.getDimensionPixelSize(R.dimen.search_bar_margins_start_end)
        val defaultMarginsTopBottom =
            context.resources.getDimensionPixelSize(R.dimen.search_bar_margins_top_bottom)
        val customMarginsStart = a.getInt(
            R.styleable.MaterialSearchBar_android_layout_marginStart,
            defaultMarginsStartEnd
        )
        val customMarginsEnd = a.getInt(
            R.styleable.MaterialSearchBar_android_layout_marginEnd,
            defaultMarginsStartEnd
        )
        val customMarginsTop = a.getInt(
            R.styleable.MaterialSearchBar_android_layout_marginTop,
            defaultMarginsTopBottom
        )
        val customMarginsBottom = a.getInt(
            R.styleable.MaterialSearchBar_android_layout_marginBottom,
            defaultMarginsTopBottom
        )
        setMargins(customMarginsStart, customMarginsTop, customMarginsEnd, customMarginsBottom)

        val defaultElevation = context.resources.getDimensionPixelSize(R.dimen.search_bar_elevation)
        val customElevation =
            a.getInt(R.styleable.MaterialSearchBar_android_elevation, defaultElevation)
        elevation = customElevation.toFloat()

        val defaultRadius = context.resources.getDimensionPixelSize(R.dimen.search_bar_radius)
        val customRadius = a.getInt(R.styleable.MaterialSearchBar_bar_radius, defaultRadius)
        setRadius(customRadius.toFloat())

        if (a.hasValue(R.styleable.MaterialSearchBar_bar_strokeColor)) {
            val customStrokeColor = a.getInt(R.styleable.MaterialSearchBar_bar_strokeColor, 0)
            setStrokeColor(customStrokeColor)
        }

        if (a.hasValue(R.styleable.MaterialSearchBar_bar_strokeWidth)) {
            val customStrokeWidth = a.getInt(R.styleable.MaterialSearchBar_bar_strokeWidth, 0)
            setStrokeWidth(customStrokeWidth)
        }

        a.recycle()
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        card?.setCardBackgroundColor(color)
    }

    fun setBackgroundColor(@Nullable color: ColorStateList?) {
        card?.setCardBackgroundColor(color)
    }

    override fun setElevation(elevation: Float) {
        card?.cardElevation = elevation
        card?.maxCardElevation = elevation
    }

    override fun getElevation(): Float {
        return card?.elevation!!
    }

    fun setRadius(radius: Float) {
        card?.radius = radius
    }

    fun getRadius(): Float {
        return card?.radius!!
    }

    fun setRippleColorResource(@ColorRes rippleColorResourceId: Int) {
        card?.setRippleColorResource(rippleColorResourceId)
    }

    fun setRippleColor(@Nullable rippleColor: ColorStateList?) {
        card?.rippleColor = rippleColor
    }

    fun setStrokeColor(@ColorInt strokeColor: Int) {
        card?.strokeColor = strokeColor
    }

    fun setStrokeColor(strokeColor: ColorStateList) {
        card?.setStrokeColor(strokeColor)
    }

    fun setStrokeWidth(@Dimension strokeWidth: Int) {
        card?.strokeWidth = strokeWidth
    }

    @Dimension
    fun getStrokeWidth(): Int {
        return card?.strokeWidth!!
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val params = card?.layoutParams as LayoutParams?
        params?.setMargins(left, top, right, bottom)
        card?.layoutParams = params
    }

    override fun setOnClickListener(l: OnClickListener?) {
        toolbar?.setOnClickListener(l)
    }

    fun getToolbar(): MaterialSearchToolbar? {
        return toolbar
    }

    fun setText(text: CharSequence?) {
        toolbar?.setText(text)
    }

    fun getText(): CharSequence? {
        return toolbar?.getText()
    }

    fun setTextHint(hint: CharSequence?) {
        toolbar?.setTextHint(hint)
    }

    fun getTextHint(): CharSequence? {
        return toolbar?.getTextHint()
    }

    // not inner, static
    class ScrollingViewBehavior : AppBarLayout.ScrollingViewBehavior {

        constructor()

        constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet)

        override fun onLayoutChild(
            parent: CoordinatorLayout,
            child: View,
            layoutDirection: Int
        ): Boolean {
            super.onLayoutChild(parent, child, layoutDirection)
            return true
        }

        override fun layoutDependsOn(
            parent: CoordinatorLayout,
            child: View,
            dependency: View
        ): Boolean {
            return if (dependency is AppBarLayout) {
                dependency.setBackgroundColor(0)
                dependency.elevation = 0.0f
                dependency.stateListAnimator = null
                true
            } else {
                false
            }
        }

    }

}