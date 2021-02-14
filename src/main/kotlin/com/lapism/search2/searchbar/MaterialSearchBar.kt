package com.lapism.search2.searchbar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import com.google.android.material.card.MaterialCardView
import com.lapism.search.R


@Suppress("unused", "MemberVisibilityCanBePrivate")
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

/*        val defaultRadius = context.resources.getDimensionPixelSize(R.dimen.search_bar)
        setRadius(a.getInt(R.styleable.MaterialSearchBar_bar_strokeColor, defaultRadius).toFloat())

        val defaultRadius = context.resources.getDimensionPixelSize(R.dimen.search_bar_radius)
        setRadius(a.getInt(R.styleable.MaterialSearchBar_bar_strokeWidth, defaultRadius).toFloat())*/

        // todo  + onclicklistener

        a.recycle()
    }

    override fun setElevation(elevation: Float) {
        card?.cardElevation = elevation
        card?.maxCardElevation = elevation
    }

    fun setRadius(radius: Float) {
        card?.radius = radius
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

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val params = card?.layoutParams as LayoutParams?
        params?.setMargins(left, top, right, bottom)
        card?.layoutParams = params
    }

    fun getToolbar(): MaterialSearchToolbar? {
        return toolbar
    }

}