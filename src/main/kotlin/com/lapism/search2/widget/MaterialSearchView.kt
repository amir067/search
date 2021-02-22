package com.lapism.search2.widget

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.LayoutTransition
import android.content.Context
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.customview.view.AbsSavedState
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.lapism.search.R
import com.lapism.search2.internal.ClippableRoundedCornerLayout
import com.lapism.search2.internal.FocusEditText
import com.lapism.search2.internal.TouchObserverFrameLayout











@Suppress("unused")
class MaterialSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), CoordinatorLayout.AttachedBehavior {

    // *********************************************************************************************
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

    @NavigationIconSupport
    @get:NavigationIconSupport
    var navigationIconSupport: Int = 0
        set(@NavigationIconSupport navigationIconSupport) {
            field = navigationIconSupport

            when (navigationIconSupport) {
                NavigationIconSupport.NONE
                -> {
                    setNavigationIcon(null)
                }
                NavigationIconSupport.MENU -> {
                    setNavigationIcon(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.search_ic_outline_menu_24
                        )
                    )
                }
                NavigationIconSupport.ARROW -> {
                    setNavigationIcon(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.search_ic_outline_arrow_back_24
                        )
                    )
                }
                NavigationIconSupport.SEARCH -> {
                    setNavigationIcon(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.search_ic_outline_search_24
                        )
                    )
                }
            }
        }

    // *********************************************************************************************
    private var scrim: View? = null
    private var root: ClippableRoundedCornerLayout? = null
    private var background: LinearLayout? = null
    private var toolbar: MaterialToolbar? = null
    private var editText: FocusEditText? = null
    private var clear: ImageButton? = null
    private var divider: View? = null
    private var container: TouchObserverFrameLayout? = null

    private var focusListener: OnFocusChangeListener? = null
    private var queryListener: OnQueryTextListener? = null
    private var clearClickListener: OnClearClickListener? = null

    // *********************************************************************************************
    init {
        View.inflate(context, R.layout.material_search_view, this)

        // TODO PARAMETRY + dalsi metody a parametry, prejmenovat parametry, anotace

        scrim = findViewById(R.id.search_view_scrim)
        root = findViewById(R.id.search_view_root)
        background = findViewById(R.id.search_view_background)
        toolbar = findViewById(R.id.search_view_toolbar)

        editText = findViewById(R.id.search_view_edit_text)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                this@MaterialSearchView.onTextChanged(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        editText?.setOnEditorActionListener { _, _, _ ->

            return@setOnEditorActionListener true
        }
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                addFocus()
            } else {
                removeFocus()
            }
        }
        val left = context.resources.getDimensionPixelSize(R.dimen.search_dp_8)
        val params = editText?.layoutParams as LinearLayout.LayoutParams
        params.setMargins(left, 0, 0, 0)
        editText?.layoutParams = params
        editText?.isFocusable = true
        editText?.isFocusableInTouchMode = true

        clear = findViewById(R.id.search_view_clear_button)
        clear?.visibility = View.GONE
        clear?.setOnClickListener {
            clearClickListener?.onClearClick()
            editText?.text?.clear()
        }

        divider = findViewById(R.id.search_view_divider)
        container = findViewById(R.id.search_view_content_container)

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.MaterialSearchView, defStyleAttr, defStyleRes
        )

        if (a.hasValue(R.styleable.MaterialSearchView_search_navigationIconSupport)) {
            navigationIconSupport = a.getInt(
                R.styleable.MaterialSearchView_search_navigationIconSupport,
                NavigationIconSupport.NONE
            )
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_navigationIcon)) {
            setNavigationIcon(a.getDrawable(R.styleable.MaterialSearchView_search_navigationIcon))
        }

        if (a.hasValue(R.styleable.MaterialSearchView_search_clearIcon)) {
            setClearIcon(a.getDrawable(R.styleable.MaterialSearchView_search_clearIcon))
        } else {
            setClearIcon(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.search_ic_outline_clear_24
                )
            )
        }

        a.recycle()
        clipChildren = false
        clipToPadding = false
        setAnimation()
    }

    private fun setAnimation(){
        /*
        android:clipChildren="false"
android:clipToPadding="false"

        * */
        //setClipChildren(false)
        background?.postDelayed({
            val b = AnimatorInflater.loadAnimator(context, R.animator.dot_animation) as AnimatorSet
/*            b.addListener(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationCancel(animation: Animator?) {
                    TODO("Not yet implemented")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    TODO("Not yet implemented")
                }
            })*/
            b.setTarget(background)
            b.start()
        }, 500)

    }

    // *********************************************************************************************
    fun getToolbar(): MaterialToolbar? {
        return toolbar
    }

    fun setNavigationIcon(@DrawableRes resId: Int) {
        toolbar?.setNavigationIcon(resId)
    }

    fun setNavigationIcon(@Nullable drawable: Drawable?) {
        toolbar?.navigationIcon = drawable
    }

    // *********************************************************************************************
    fun setClearIcon(@DrawableRes resId: Int) {
        clear?.setImageResource(resId)
    }

    fun setClearIcon(@Nullable drawable: Drawable?) {
        clear?.setImageDrawable(drawable)
    }

    fun setClearIconColorFilter(color: Int) {
        clear?.setColorFilter(color)
    }

    fun setClearIconColorFilter(color: Int, mode: PorterDuff.Mode) {
        clear?.setColorFilter(color, mode)
    }

    fun setClearIconColorFilter(cf: ColorFilter?) {
        clear?.colorFilter = cf
    }

    fun clearClearIconColorFilter() {
        clear?.clearColorFilter()
    }

    fun setClearIconContentDescription(contentDescription: CharSequence) {
        clear?.contentDescription = contentDescription
    }

    // *********************************************************************************************
    fun setText(text: CharSequence?) {
        editText?.setText(text)
    }

    @Nullable
    fun getText(): Editable? {
        return editText?.text
    }

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

    fun setTextHint(hint: CharSequence?) {
        editText?.hint = hint
    }

    fun setTextHint(@StringRes resid: Int) {
        editText?.setHint(resid)
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

    fun setTextHintColor(@ColorInt color: Int) {
        editText?.setHintTextColor(color)
    }

    fun setTextClearOnBackPressed(clearFocusOnBackPressed: Boolean) {
        editText?.setTextClearOnBackPressed(clearFocusOnBackPressed)
    }

    // *********************************************************************************************
    fun setDividerColor(@ColorInt color: Int) {
        divider?.setBackgroundColor(color)
    }

    fun setDividerVisibility(visibility: Int) {
        divider?.visibility = visibility
    }

    fun setScrimColor(@ColorInt color: Int) {
        scrim?.setBackgroundColor(color)
    }

    // *********************************************************************************************
    fun setOnFocusChangeListener(listener: OnFocusChangeListener) {
        focusListener = listener
    }

    fun setOnQueryTextListener(listener: OnQueryTextListener) {
        queryListener = listener
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
                editText?.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }

    // *********************************************************************************************
    private fun onTextChanged(s: CharSequence) {
        //         if (!TextUtils.isEmpty(newText)) {
        if (s.isNotEmpty()) {
            clear?.visibility = View.VISIBLE
        } else {
            clear?.visibility = View.GONE
        }

        queryListener?.onQueryTextChange(s)
    }

    private fun onSubmitQuery() {
        // TODO
    }

    private fun addFocus() {
        focusListener?.onFocusChange(true)
        showKeyboard()
    }

    private fun removeFocus() {
        focusListener?.onFocusChange(false)
        hideKeyboard()
    }

    private fun setLayoutHeight(height: Int) {
        val params = background?.layoutParams
        params?.height = height
        params?.width = ViewGroup.LayoutParams.MATCH_PARENT
        background?.layoutParams = params
    }

    private fun setTransition() {
        val mTransition = LayoutTransition()
        mTransition.enableTransitionType(LayoutTransition.CHANGING)
        mTransition.addTransitionListener(object : LayoutTransition.TransitionListener {
            override fun startTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {

            }

            override fun endTransition(
                transition: LayoutTransition?,
                container: ViewGroup?,
                view: View?,
                transitionType: Int
            ) {

            }
        })
        mTransition.setDuration(300L)
        layoutTransition = mTransition
    }

    // *********************************************************************************************
    override fun setElevation(elevation: Float) {
        toolbar?.elevation = elevation
    }

    override fun getElevation(): Float {
        return toolbar?.elevation!!
    }

    // TODO MORE + dle stareho SearchView + anotace
    override fun setBackgroundColor(@ColorInt color: Int) {
        background?.setBackgroundColor(color)
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return editText?.requestFocus(direction, previouslyFocusedRect)!!
    }

    override fun clearFocus() {
        editText?.clearFocus()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState?) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state?.superState)
        setText(state?.text!!)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState = super.onSaveInstanceState()?.let { SavedState(it) }
        savedState?.text = getText().toString()
        savedState?.visibility = root?.visibility!!
        return savedState
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return Behavior()
    }

    // *********************************************************************************************
    interface OnFocusChangeListener {

        fun onFocusChange(hasFocus: Boolean)
    }

    interface OnQueryTextListener {

        fun onQueryTextChange(newText: CharSequence): Boolean

        fun onQueryTextSubmit(query: CharSequence): Boolean
    }

    interface OnClearClickListener {

        fun onClearClick()
    }

    // *********************************************************************************************
    // NOT INNER CLASS >> STATIC
    class SavedState : AbsSavedState {

        var text: String? = null
        var visibility: Int = View.GONE

        constructor(superState: Parcelable) : super(superState)

        private constructor(source: Parcel, loader: ClassLoader? = null) : super(source, loader) {
            text = source.readString()
            visibility = source.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeString(text)
            dest.writeInt(visibility)
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

    class Behavior : CoordinatorLayout.Behavior<MaterialSearchView>() {

        override fun layoutDependsOn(
            parent: CoordinatorLayout,
            child: MaterialSearchView,
            dependency: View
        ): Boolean {
            return if (dependency is AppBarLayout) {
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
                return true
            }
            return super.onDependentViewChanged(parent, child, dependency)
        }

    }

}

// znovu vsechny override tridy a parametry, by lazy;,dependence
//android:clipToPadding="false"
