package com.lapism.search2.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
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

    private var root: ClippableRoundedCornerLayout? = null
    private var toolbar: MaterialToolbar? = null
    private var editText: FocusEditText? = null
    private var container: TouchObserverFrameLayout? = null

    init {
        View.inflate(context, R.layout.material_search_view, this)

        // TODO PARAMETRY

        val scrim = findViewById<View>(R.id.search_view_scrim)
        root = findViewById(R.id.search_view_root)
        val background = findViewById<LinearLayout>(R.id.search_view_background)
        toolbar = findViewById(R.id.search_view_toolbar)

        editText = findViewById(R.id.search_view_edit_text)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

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

        val clear = findViewById<ImageButton>(R.id.search_view_clear_button)

        val divider = findViewById<View>(R.id.search_view_divider)

        container = findViewById(R.id.search_view_content_container)

        val left = context.resources.getDimensionPixelSize(R.dimen.search_dp_8)
        val params = editText?.layoutParams as LinearLayout.LayoutParams
        params.setMargins(left, 0, 0, 0)

        editText?.layoutParams = params
        editText?.isFocusable = true
        editText?.isFocusableInTouchMode = true
    }

    fun getToolbar(): MaterialToolbar? {
        return toolbar
    }

    fun setText(text: CharSequence?) {
        editText?.setText(text)
    }

    @Nullable
    fun getText(): Editable? {
        return editText?.text
    }

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

    private fun addFocus() {
        showKeyboard()
    }

    private fun removeFocus() {
        hideKeyboard()
    }
    //android:clipToPadding="false"

    private fun showKeyboard() {
        if (!isInEditMode) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(
                editText,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }

    private fun hideKeyboard() {
        if (!isInEditMode) {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
            )
        }
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return editText?.requestFocus(direction, previouslyFocusedRect)!!
    }

    override fun clearFocus() {
        editText?.clearFocus()
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return Behavior()
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

    // NOT INNER CLASS >> STATIC
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

}

// znovu vsechny override tridy a parametry, by lazy

