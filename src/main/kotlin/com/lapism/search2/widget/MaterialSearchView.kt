package com.lapism.search2.widget

import android.content.Context
import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
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

        val listener = OnTouchListener { v, event -> true }
        container?.setOnTouchListener(listener)

        showKeyboard()
    }

    fun getToolbar(): MaterialToolbar? {
        return toolbar
    }

    fun getText(): Editable? {
        return editText?.text
    }

    fun setText(text: CharSequence?) {
        editText?.setText(text)
    }

    fun addFocus() {
        showKeyboard()
    }

    fun removeFocus() {
        hideKeyboard()
    }

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

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    // TODO OTHERS
/*    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        container?.addView(child, index, params)
    }*/

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

    // not inner, static
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

