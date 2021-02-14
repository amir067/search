package com.lapism.search2.internal

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

/**
 * @hide
 */
// @RestrictTo(RestrictTo.Scope.LIBRARY) TODO
class SearchText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var clearFocusOnBackPressed: Boolean = false

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP && clearFocusOnBackPressed) {
            if (hasFocus()) {
                clearFocus()
                return true
            }
        }
        return super.onKeyPreIme(keyCode, event)
    }

    override fun clearFocus() {
        super.clearFocus()
        text?.clear()
    }

    fun setClearFocusOnBackPressed(clearFocus: Boolean) {
        clearFocusOnBackPressed = clearFocus
    }

}
