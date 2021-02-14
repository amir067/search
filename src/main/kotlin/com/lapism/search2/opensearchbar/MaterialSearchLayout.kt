package com.lapism.search2.opensearchbar

import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.annotation.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.lapism.search.R
import com.lapism.search2.internal.SearchText


@Suppress("unused")
abstract class MaterialSearchLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), View.OnClickListener {

    // *********************************************************************************************


    // *********************************************************************************************
    protected var scrim: View? = null
    private var clip: ClippableLayout? = null
    private var card: MaterialCardView? = null
    private var toolbar: MaterialToolbar? = null
    protected var editText: SearchText? = null
    private var clearButton: ImageButton? = null
    protected var divider: View? = null
    protected var contentContainer: TouchObserverFrameLayout? = null

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

            /*when (navigationIconSupport) {
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
            }*/
        }





    // *********************************************************************************************
    protected abstract fun addFocus()

    protected abstract fun removeFocus()

    // *********************************************************************************************
    protected fun init() {
        scrim = findViewById(R.id.search_scrim)
        scrim?.visibility = View.GONE

        clip = findViewById(R.id.search_clip)
        clip?.isFocusable = true
        clip?.isFocusableInTouchMode = true

        //card = findViewById(R.id.search_card)
        //margins = Margins.NO_FOCUS

        toolbar = findViewById(R.id.search_toolbar)

        editText = findViewById(R.id.search_edit_text)
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

        clearButton = findViewById(R.id.search_clear_button)
        clearButton?.visibility = View.GONE
        clearButton?.setOnClickListener(this)

        divider = findViewById(R.id.search_divider)
        divider?.visibility = View.GONE

        contentContainer = findViewById(R.id.search_content_container)
        contentContainer?.visibility = View.GONE

        isFocusable = true
        isFocusableInTouchMode = true
    }

    // *********************************************************************************************
    /*fun setNavigationIconVisibility(visibility: Int) {
        toolbar?.visibility = visibility
    }*/

    /*fun setNavigationIconImageResource(@DrawableRes resId: Int) {
        mImageViewNavigation?.setImageResource(resId)
    }*/

    fun setNavigationIconImageDrawable(@Nullable drawable: Drawable?) {
        toolbar?.navigationIcon = drawable
    }

    /*fun getNavigationIconImageDrawable(): Drawable? {
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
    }*/

    // *********************************************************************************************
    fun setClearIconImageResource(@DrawableRes resId: Int) {
        clearButton?.setImageResource(resId)
    }

    fun setClearIconImageDrawable(@Nullable drawable: Drawable?) {
        clearButton?.setImageDrawable(drawable)
    }

    fun setClearIconColorFilter(color: Int) {
        clearButton?.setColorFilter(color)
    }

    fun setClearIconColorFilter(color: Int, mode: PorterDuff.Mode) {
        clearButton?.setColorFilter(color, mode)
    }

    fun setClearIconColorFilter(cf: ColorFilter?) {
        clearButton?.colorFilter = cf
    }

    fun clearClearIconColorFilter() {
        clearButton?.clearColorFilter()
    }

    fun setClearIconContentDescription(contentDescription: CharSequence) {
        clearButton?.contentDescription = contentDescription
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
    fun getTextQuery(): CharSequence? {
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
        /*val params = mLinearLayout?.layoutParams
        params?.height = height
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        mLinearLayout?.layoutParams = params*/
    }

    // *********************************************************************************************
    private fun onTextChanged(newText: CharSequence) {
        if (!TextUtils.isEmpty(newText)) {
            clearButton?.visibility = View.VISIBLE
        } else {
            clearButton?.visibility = View.GONE
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
    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SearchSavedState(superState!!)
        if (editText?.text!!.isNotEmpty()) {
            ss.query = editText?.text
        }
        ss.hasFocus = editText?.hasFocus()!!
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SearchSavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        if (state.hasFocus) {
            editText?.requestFocus()
        }
        if (state.query != null) {
            setTextQuery(state.query, false)
        }
        //requestLayout()
    }

    /*
        public final Parcelable onSaveInstanceState() {
        String str;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence text = getText();
        if (text == null) {
            str = null;
        } else {
            str = text.toString();
        }
        savedState.f12075a = str;
        return savedState;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f3919d);
        setText((CharSequence) savedState.f12075a);
    }
    * */

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return if (!isFocusable) {
            false
        } else {
            // fixme
            editText?.requestFocus(direction, previouslyFocusedRect)!!
        }
    }

    override fun clearFocus() {
        super.clearFocus()
        editText?.clearFocus()
    }

    override fun onClick(view: View?) {
        /* if (view === mImageViewNavigation) {
             if (mOnNavigationClickListener != null) {
                 mOnNavigationClickListener?.onNavigationClick(mSearchText?.hasFocus()!!)
             }
         } else if (view === mImageViewClear) {
             if (mSearchText?.text!!.isNotEmpty()) {
                 mSearchText?.text!!.clear()
             }
             if (mOnClearClickListener != null) {
                 mOnClearClickListener?.onClearClick()
             }
         }*/
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

}


// *********************************************************************************************

/*mRecyclerView = findViewById(R.id.search_recycler_view)
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
})*/

/*fun setAdapterLayoutManager(@Nullable layout: RecyclerView.LayoutManager?) {
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
}*/