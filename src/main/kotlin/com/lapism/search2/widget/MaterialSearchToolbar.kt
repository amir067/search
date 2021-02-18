package com.lapism.search2.widget

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.annotation.AttrRes
import com.google.android.material.appbar.MaterialToolbar
import com.lapism.search.R
import com.lapism.search2.internal.FocusEditText


@Suppress("unused")
class MaterialSearchToolbar : MaterialToolbar {

    private var textView: TextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.material_search_toolbar, this)

        textView = findViewById(R.id.search_toolbar_text_view)
    }

    override fun setTitle(resId: Int) {

    }

    override fun setTitle(title: CharSequence?) {

    }

    override fun setSubtitle(resId: Int) {

    }

    override fun setSubtitle(subtitle: CharSequence?) {

    }

    override fun setElevation(elevation: Float) {

    }

/*    private fun m32261P(i: Int, i2: Int): Int {
        return if (i == 0) i2 else i
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (layoutParams is MarginLayoutParams) {
            val resources = resources
            val dimensionPixelSize =
                resources.getDimensionPixelSize(R.dimen.google_opensearchbar_margin_horizontal)
            val dimensionPixelSize2 =
                resources.getDimensionPixelSize(R.dimen.google_opensearchbar_margin_vertical)
            val marginLayoutParams = layoutParams as MarginLayoutParams
            marginLayoutParams.leftMargin =
                m32261P(marginLayoutParams.leftMargin, dimensionPixelSize)
            marginLayoutParams.topMargin =
                m32261P(marginLayoutParams.topMargin, dimensionPixelSize2)
            marginLayoutParams.rightMargin =
                m32261P(marginLayoutParams.rightMargin, dimensionPixelSize)
            marginLayoutParams.bottomMargin =
                m32261P(marginLayoutParams.bottomMargin, dimensionPixelSize2)
        }
    }*/

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(info)
        info?.className = FocusEditText::class.java.canonicalName
        var h: CharSequence? = getText()
        val isEmpty = TextUtils.isEmpty(h)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            info?.hintText = getHint()
            info?.isShowingHintText = isEmpty
        }
        if (isEmpty) {
            h = getHint()
        }
        info?.text = h
    }

    fun setText(text: CharSequence?) {
        textView?.text = text
    }

    fun getText(): CharSequence? {
        return textView?.text
    }

    fun setHint(hint: CharSequence?) {
        textView?.hint = hint
    }

    fun getHint(): CharSequence? {
        return textView?.hint
    }

}