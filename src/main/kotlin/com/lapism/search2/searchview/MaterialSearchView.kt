package com.lapism.search2.searchview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.lapism.search.R
import com.lapism.search2.internal.FocusEditText


class MaterialSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), CoordinatorLayout.AttachedBehavior {

    private var editText: FocusEditText? = null

    init {
        View.inflate(context, R.layout.material_search_view, this)

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

            } else {

            }
        }
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return Behavior()
    }

}

// znovu vsechny override tridy a parametry, inner class a class, ta kodovina
