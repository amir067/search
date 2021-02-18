package com.lapism.search.internal

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.customview.view.AbsSavedState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.lapism.search.R
import com.lapism.search2.internal.FocusEditText

/**
 * @hide
 */
// @RestrictTo(RestrictTo.Scope.LIBRARY) TODO
@Suppress("unused")
abstract class MaterialSearchLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), View.OnClickListener {

    // *********************************************************************************************
    // Better way than enum class :-)
    @IntDef(
        NavigationIconSupport.NONE,
        NavigationIconSupport.MENU,
        NavigationIconSupport.ARROW,
        NavigationIconSupport.SEARCH
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class NavigationIconSupport {
        companion object {
            const val NONE = 0
            const val MENU = 1
            const val ARROW = 2
            const val SEARCH = 3
        }
    }

    @IntDef(
        Margins.NO_FOCUS,
        Margins.FOCUS
    )
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class Margins {
        companion object {
            const val NO_FOCUS = 4
            const val FOCUS = 5
        }
    }

    // *********************************************************************************************
    private var mImageViewMic: ImageButton? = null
    protected var mRecyclerView: RecyclerView? = null
    private var mMaterialCardView: MaterialCardView? = null
    protected var editText: FocusEditText? = null
    protected var scrim: View? = null
    protected var divider: View? = null
    protected var mViewAnim: View? = null
    private var mLinearLayout: LinearLayout? = null
    private var mImageViewNavigation: ImageButton? = null
    private var mImageViewClear: ImageButton? = null

    protected var mOnFocusChangeListener: OnFocusChangeListener? = null
    private var mOnQueryTextListener: OnQueryTextListener? = null
    private var mOnNavigationClickListener: OnNavigationClickListener? = null
    private var mOnMicClickListener: OnMicClickListener? = null
    private var mOnClearClickListener: OnClearClickListener? = null

    // *********************************************************************************************
    @NavigationIconSupport
    @get:NavigationIconSupport
    var navigationIconSupport: Int = 0
        set(@NavigationIconSupport navigationIconSupport) {
            field = navigationIconSupport

            when (navigationIconSupport) {
                NavigationIconSupport.NONE
                -> {
                    setNavigationIconImageDrawable(null)
                }
                NavigationIconSupport.MENU -> {
                    setNavigationIconImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.search_ic_outline_menu_24
                        )
                    )
                }
                NavigationIconSupport.ARROW -> {
                    setNavigationIconImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.search_ic_outline_arrow_back_24
                        )
                    )
                }
                NavigationIconSupport.SEARCH -> {
                    setNavigationIconImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.search_ic_outline_search_24
                        )
                    )
                }
            }
        }

    @Margins
    @get:Margins
    protected var margins: Int = 0
        set(@Margins margins) {
            field = margins

            val left: Int
            val top: Int
            val right: Int
            val bottom: Int
            val params = mMaterialCardView?.layoutParams as LayoutParams?

            when (margins) {
                Margins.NO_FOCUS -> {
                    left =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_left_right)
                    top =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_top_bottom)
                    right =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_left_right)
                    bottom =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_top_bottom)

                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    params?.setMargins(left, top, right, bottom)
                    mMaterialCardView?.layoutParams = params
                }
                Margins.FOCUS -> {
                    left =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_focus)
                    top =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_focus)
                    right =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_focus)
                    bottom =
                        context.resources.getDimensionPixelSize(R.dimen.search_margins_focus)

                    params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    params?.setMargins(left, top, right, bottom)
                    mMaterialCardView?.layoutParams = params
                }
            }
        }

    // *********************************************************************************************
    protected abstract fun addFocus()

    protected abstract fun removeFocus()

    // *********************************************************************************************
    protected fun init() {
        mLinearLayout = findViewById(R.id.search_linear_layout)

        mImageViewNavigation = findViewById(R.id.search_image_view_navigation)
        mImageViewNavigation?.setOnClickListener(this)

        mImageViewMic = findViewById(R.id.search_image_view_mic)
        mImageViewMic?.setOnClickListener(this)

        mImageViewClear = findViewById(R.id.search_image_view_clear)
        mImageViewClear?.visibility = View.GONE
        mImageViewClear?.setOnClickListener(this)

        editText = findViewById(R.id.search_search_edit_text)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                this@MaterialSearchLayout.onTextChanged(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        editText?.setOnEditorActionListener { _, _, _ ->
            onSubmitQuery()
            return@setOnEditorActionListener true // true
        }
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                addFocus()
            } else {
                removeFocus()
            }
        }

        mRecyclerView = findViewById(R.id.search_recycler_view)
        mRecyclerView?.visibility = View.GONE
        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mRecyclerView?.isNestedScrollingEnabled = false
        mRecyclerView?.itemAnimator = DefaultItemAnimator()
        mRecyclerView?.overScrollMode = View.OVER_SCROLL_NEVER
        mRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideKeyboard()
                }
            }
        })

        scrim = findViewById(R.id.search_view_shadow)
        scrim?.visibility = View.GONE

        divider = findViewById(R.id.search_view_divider)
        divider?.visibility = View.GONE

        mViewAnim = findViewById(R.id.search_view_anim)
        mViewAnim?.visibility = View.GONE

        mMaterialCardView = findViewById(R.id.search_material_card_view)
        margins = Margins.NO_FOCUS

        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = true
    }

    // *********************************************************************************************
    fun setNavigationIconVisibility(visibility: Int) {
        mImageViewNavigation?.visibility = visibility
    }

    fun setNavigationIconImageResource(@DrawableRes resId: Int) {
        mImageViewNavigation?.setImageResource(resId)
    }

    fun setNavigationIconImageDrawable(@Nullable drawable: Drawable?) {
        mImageViewNavigation?.setImageDrawable(drawable)
    }

    fun getNavigationIconImageDrawable(): Drawable? {
        return mImageViewNavigation?.drawable
    }

    fun setNavigationIconColorFilter(color: Int) {
        mImageViewNavigation?.setColorFilter(color)
    }

    fun setNavigationIconColorFilter(color: Int, mode: PorterDuff.Mode) {
        mImageViewNavigation?.setColorFilter(color, mode)
    }

    fun setNavigationIconColorFilter(cf: ColorFilter?) {
        mImageViewNavigation?.colorFilter = cf
    }

    fun clearNavigationIconColorFilter() {
        mImageViewNavigation?.clearColorFilter()
    }

    fun setNavigationIconContentDescription(contentDescription: CharSequence) {
        mImageViewNavigation?.contentDescription = contentDescription
    }

    // *********************************************************************************************
    fun setMicIconImageResource(@DrawableRes resId: Int) {
        mImageViewMic?.setImageResource(resId)
    }

    fun setMicIconImageDrawable(@Nullable drawable: Drawable?) {
        mImageViewMic?.setImageDrawable(drawable)
    }

    fun setMicIconColorFilter(color: Int) {
        mImageViewMic?.setColorFilter(color)
    }

    fun setMicIconColorFilter(color: Int, mode: PorterDuff.Mode) {
        mImageViewMic?.setColorFilter(color, mode)
    }

    fun setMicIconColorFilter(cf: ColorFilter?) {
        mImageViewMic?.colorFilter = cf
    }

    fun clearMicIconColorFilter() {
        mImageViewMic?.clearColorFilter()
    }

    fun setMicIconContentDescription(contentDescription: CharSequence) {
        mImageViewMic?.contentDescription = contentDescription
    }

    // *********************************************************************************************
    fun setClearIconImageResource(@DrawableRes resId: Int) {
        mImageViewClear?.setImageResource(resId)
    }

    fun setClearIconImageDrawable(@Nullable drawable: Drawable?) {
        mImageViewClear?.setImageDrawable(drawable)
    }

    fun setClearIconColorFilter(color: Int) {
        mImageViewClear?.setColorFilter(color)
    }

    fun setClearIconColorFilter(color: Int, mode: PorterDuff.Mode) {
        mImageViewClear?.setColorFilter(color, mode)
    }

    fun setClearIconColorFilter(cf: ColorFilter?) {
        mImageViewClear?.colorFilter = cf
    }

    fun clearClearIconColorFilter() {
        mImageViewClear?.clearColorFilter()
    }

    fun setClearIconContentDescription(contentDescription: CharSequence) {
        mImageViewClear?.contentDescription = contentDescription
    }

    // *********************************************************************************************
    fun setAdapterLayoutManager(@Nullable layout: RecyclerView.LayoutManager?) {
        mRecyclerView?.layoutManager = layout
    }

    // only when height == match_parent
    fun setAdapterHasFixedSize(hasFixedSize: Boolean) {
        mRecyclerView?.setHasFixedSize(hasFixedSize)
    }

    fun addAdapterItemDecoration(@NonNull decor: RecyclerView.ItemDecoration) {
        mRecyclerView?.addItemDecoration(decor)
    }

    fun removeAdapterItemDecoration(@NonNull decor: RecyclerView.ItemDecoration) {
        mRecyclerView?.removeItemDecoration(decor)
    }

    fun setAdapter(@Nullable adapter: RecyclerView.Adapter<*>?) {
        mRecyclerView?.adapter = adapter
    }

    @Nullable
    fun getAdapter(): RecyclerView.Adapter<*>? {
        return mRecyclerView?.adapter
    }

    // *********************************************************************************************
    /**
     * Typeface.NORMAL
     * Typeface.BOLD
     * Typeface.ITALIC
     * Typeface.BOLD_ITALIC
     *
     * Typeface.DEFAULT
     * Typeface.DEFAULT_BOLD
     * Typeface.SANS_SERIF
     * Typeface.SERIF
     * Typeface.MONOSPACE
     *
     * Typeface.create(Typeface.NORMAL, Typeface.DEFAULT)
     */
    fun setTextTypeface(@Nullable tf: Typeface?) {
        editText?.typeface = tf
    }

    fun getTextTypeface(): Typeface? {
        return editText?.typeface
    }

    fun setTextInputType(type: Int) {
        editText?.inputType = type
    }

    fun getTextInputType(): Int? {
        return editText?.inputType
    }

    fun setTextImeOptions(imeOptions: Int) {
        editText?.imeOptions = imeOptions
    }

    fun getTextImeOptions(): Int? {
        return editText?.imeOptions
    }

    fun setTextQuery(query: CharSequence?, submit: Boolean) {
        editText?.setText(query)
        if (query != null) {
            editText?.setSelection(editText?.length()!!)
        }
        if (submit && !TextUtils.isEmpty(query)) {
            onSubmitQuery()
        }
    }

    @Nullable
    fun getTextQuery(): Editable? {
        return editText?.text
    }

    fun setTextHint(hint: CharSequence?) {
        editText?.hint = hint
    }

    fun getTextHint(): CharSequence? {
        return editText?.hint
    }

    fun setTextColor(@ColorInt color: Int) {
        editText?.setTextColor(color)
    }

    fun setTextSize(size: Float) {
        editText?.textSize = size
    }

    fun setTextGravity(gravity: Int) {
        editText?.gravity = gravity
    }

    fun setTextHint(@StringRes resid: Int) {
        editText?.setHint(resid)
    }

    fun setTextHintColor(@ColorInt color: Int) {
        editText?.setHintTextColor(color)
    }

    fun setClearFocusOnBackPressed(clearFocusOnBackPressed: Boolean) {
        editText?.setClearFocusOnBackPressed(clearFocusOnBackPressed)
    }

    // *********************************************************************************************
    override fun setBackgroundColor(@ColorInt color: Int) {
        mMaterialCardView?.setCardBackgroundColor(color)
    }

    fun setBackgroundColor(@Nullable color: ColorStateList?) {
        mMaterialCardView?.setCardBackgroundColor(color)
    }

    override fun setElevation(elevation: Float) {
        mMaterialCardView?.cardElevation = elevation
        mMaterialCardView?.maxCardElevation = elevation
    }

    override fun getElevation(): Float {
        return mMaterialCardView?.elevation!!
    }

    fun setBackgroundRadius(radius: Float) {
        mMaterialCardView?.radius = radius
    }

    fun getBackgroundRadius(): Float {
        return mMaterialCardView?.radius!!
    }

    fun setBackgroundRippleColor(@ColorRes rippleColorResourceId: Int) {
        mMaterialCardView?.setRippleColorResource(rippleColorResourceId)
    }

    fun setBackgroundRippleColorResource(@Nullable rippleColor: ColorStateList?) {
        mMaterialCardView?.rippleColor = rippleColor
    }

    fun setBackgroundStrokeColor(@ColorInt strokeColor: Int) {
        mMaterialCardView?.strokeColor = strokeColor
    }

    fun setBackgroundStrokeColor(strokeColor: ColorStateList) {
        mMaterialCardView?.setStrokeColor(strokeColor)
    }

    fun setBackgroundStrokeWidth(@Dimension strokeWidth: Int) {
        mMaterialCardView?.strokeWidth = strokeWidth
    }

    @Dimension
    fun getBackgroundStrokeWidth(): Int {
        return mMaterialCardView?.strokeWidth!!
    }

    // *********************************************************************************************
    fun setDividerColor(@ColorInt color: Int) {
        divider?.setBackgroundColor(color)
    }

    fun setScrimColor(@ColorInt color: Int) {
        scrim?.setBackgroundColor(color)
    }

    // *********************************************************************************************
    fun setOnFocusChangeListener(listener: OnFocusChangeListener) {
        mOnFocusChangeListener = listener
    }

    fun setOnQueryTextListener(listener: OnQueryTextListener) {
        mOnQueryTextListener = listener
    }

    fun setOnNavigationClickListener(listener: OnNavigationClickListener) {
        mOnNavigationClickListener = listener
    }

    fun setOnMicClickListener(listener: OnMicClickListener) {
        mOnMicClickListener = listener
    }

    fun setOnClearClickListener(listener: OnClearClickListener) {
        mOnClearClickListener = listener
    }

    // *********************************************************************************************
    fun showKeyboard() {
        if (!isInEditMode) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                editText,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }

    fun hideKeyboard() {
        if (!isInEditMode) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }

    // *********************************************************************************************
    protected fun setLayoutHeight(height: Int) {
        val params = mLinearLayout?.layoutParams
        params?.height = height
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        mLinearLayout?.layoutParams = params
    }

    // *********************************************************************************************
    private fun onTextChanged(newText: CharSequence) {
        if (!TextUtils.isEmpty(newText)) {
            mImageViewMic?.visibility = View.GONE
            mImageViewClear?.visibility = View.VISIBLE
        } else {
            mImageViewClear?.visibility = View.GONE
            if (editText?.hasFocus()!!) {
                mImageViewMic?.visibility = View.VISIBLE
            } else {
                mImageViewMic?.visibility = View.GONE
            }
        }

        if (mOnQueryTextListener != null) {
            mOnQueryTextListener?.onQueryTextChange(newText)
        }
    }

    private fun onSubmitQuery() {
        val query = editText?.text
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            if (mOnQueryTextListener == null || !mOnQueryTextListener!!.onQueryTextSubmit(query.toString())) {
                editText?.text = query
            }
        }
    }

    // *********************************************************************************************
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState?) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state?.superState)
        setTextQuery(state?.text, false)
        if (state?.hasFocus!!) {
            editText?.requestFocus()
        }
        // TODO requestLayout(),
        //  FIXME invalidate()
    }

    override fun onSaveInstanceState(): Parcelable? {
        val ss = super.onSaveInstanceState()?.let { SavedState(it) }
        ss?.text = getTextQuery()
        ss?.hasFocus = editText?.hasFocus()!!
        return ss
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return if (!isFocusable) {
            false
        } else {
            editText?.requestFocus(direction, previouslyFocusedRect)!!
        }
    }

    override fun clearFocus() {
        super.clearFocus()
        editText?.clearFocus()
    }

    override fun onClick(view: View?) {
        if (view === mImageViewNavigation) {
            if (mOnNavigationClickListener != null) {
                mOnNavigationClickListener?.onNavigationClick(editText?.hasFocus()!!)
            }
        } else if (view === mImageViewMic) {
            if (mOnMicClickListener != null) {
                mOnMicClickListener?.onMicClick()
            }
        } else if (view === mImageViewClear) {
            if (editText?.text!!.isNotEmpty()) {
                editText?.text!!.clear()
            }
            if (mOnClearClickListener != null) {
                mOnClearClickListener?.onClearClick()
            }
        }
    }

    // *********************************************************************************************
    interface OnFocusChangeListener {

        fun onFocusChange(hasFocus: Boolean)
    }

    interface OnQueryTextListener {

        fun onQueryTextChange(newText: CharSequence): Boolean

        fun onQueryTextSubmit(query: CharSequence): Boolean
    }

    interface OnNavigationClickListener {

        fun onNavigationClick(hasFocus: Boolean)
    }

    interface OnMicClickListener {

        fun onMicClick()
    }

    interface OnClearClickListener {

        fun onClearClick()
    }

    // *********************************************************************************************
    class SavedState : AbsSavedState { // View.BaseSavedState

        var text: CharSequence? = null
        var hasFocus = false

        constructor(superState: Parcelable) : super(superState)

        private constructor(source: Parcel, loader: ClassLoader? = null) : super(source, loader)

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            TextUtils.writeToParcel(text, dest, flags)
            dest.writeInt(if (hasFocus) 1 else 0)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object :
                Parcelable.ClassLoaderCreator<SavedState> {
                override fun createFromParcel(source: Parcel, loader: ClassLoader): SavedState {
                    return SavedState(source, loader)
                }

                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }

    }

}