package com.lapism.search2.opensearchbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.lapism.search.R
import com.lapism.search2.opensearchbar.MaterialSearchLayout


class MaterialSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : MaterialSearchLayout(context, attrs, defStyleAttr, defStyleRes),
    CoordinatorLayout.AttachedBehavior {

    // *********************************************************************************************
    init {
        inflate(context, R.layout.material_search_view, this)
        init()


        if (a.hasValue(R.styleable.MaterialSearchView_search_navigationIconSupport)) {
            navigationIconSupport = a.getInt(
                R.styleable.MaterialSearchView_search_navigationIconSupport,
                NavigationIconSupport.NONE
            )
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_navigationIcon)) {
            //setNavigationIconImageDrawable(a.getDrawable(R.styleable.MaterialSearchView_search_navigationIcon))
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_clearIcon)) {
            setClearIconImageDrawable(a.getDrawable(R.styleable.MaterialSearchView_search_clearIcon))
        } else {
            setClearIconImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.search_ic_outline_clear_24
                )
            )
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_dividerColor)) {
            setDividerColor(a.getInt(R.styleable.MaterialSearchView_search_dividerColor, 0))
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_scrimColor)) {
            setScrimColor(a.getInt(R.styleable.MaterialSearchView_search_scrimColor, 0))
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_textHint)) {
            setTextHint(a.getText(R.styleable.MaterialSearchView_search_textHint))
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_strokeColor)) {
            // setBackgroundStrokeColor(a.getInt(R.styleable.MaterialSearchView_search_strokeColor, 0))
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_strokeWidth)) {
            // setBackgroundStrokeWidth(a.getInt(R.styleable.MaterialSearchView_search_strokeWidth, 0))
        }

        /*val defaultTransitionDuration = context.resources.getInteger(R.integer.search_animation_duration)
        setTransitionDuration(
            a.getInt(
                R.styleable.MaterialSearchView_search_transitionDuration,
                defaultTransitionDuration
            ).toLong()
        )*/



        val imeOptions = a.getInt(R.styleable.MaterialSearchView_android_imeOptions, -1)
        if (imeOptions != -1) {
            setTextImeOptions(imeOptions)
        }

        val inputType = a.getInt(R.styleable.MaterialSearchView_android_inputType, -1)
        if (inputType != -1) {
            setTextInputType(inputType)
        }


    }

    // *********************************************************************************************
    override fun addFocus() {
        mOnFocusChangeListener?.onFocusChange(true)
        showKeyboard()



        /*setBackgroundStrokeWidth(context.resources.getDimensionPixelSize(R.dimen.search_stroke_width_focus))
        setBackgroundRadius(resources.getDimensionPixelSize(R.dimen.search_radius_focus).toFloat())*/
        elevation =
            context.resources.getDimensionPixelSize(R.dimen.search_elevation_focus).toFloat()

        val left = context.resources.getDimensionPixelSize(R.dimen.search_dp_16)
        val params = editText?.layoutParams as LinearLayout.LayoutParams
        params.setMargins(left, 0, 0, 0)
        editText?.layoutParams = params

        margins = Margins.FOCUS
        setLayoutHeight(context.resources.getDimensionPixelSize(R.dimen.search_layout_height_focus))

        scrim?.visibility = View.VISIBLE
        divider?.visibility = View.VISIBLE
        contentContainer?.visibility = View.VISIBLE

        // layoutTransition = null
    }

    override fun removeFocus() {
        // layoutTransition = mTransition

        mOnFocusChangeListener?.onFocusChange(false)
        hideKeyboard()

        val params = editText?.layoutParams as LinearLayout.LayoutParams
        params.setMargins(0, 0, 0, 0)
        editText?.layoutParams = params

        setLayoutHeight(context.resources.getDimensionPixelSize(R.dimen.search_layout_height))
        margins = Margins.NO_FOCUS

        scrim?.visibility = View.GONE
        contentContainer?.visibility = View.GONE
        divider?.visibility = View.GONE
    }

}
