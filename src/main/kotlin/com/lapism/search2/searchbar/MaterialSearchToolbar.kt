package com.lapism.search2.searchbar

import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.annotation.AttrRes
import com.google.android.material.appbar.MaterialToolbar
import com.lapism.search.R
import com.lapism.search2.internal.FocusEditText


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

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(info)
        info?.className = FocusEditText::class.java.canonicalName
        var h: CharSequence? = getText()
        val isEmpty = TextUtils.isEmpty(h)
        if (Build.VERSION.SDK_INT >= 26) {
            info?.hintText = getHint()
            info?.isShowingHintText = isEmpty
        }
        if (isEmpty) {
            h = getHint()
        }
        info?.text = h
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
    }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()
    }

    fun setText(text: CharSequence) {
        textView?.text = text
    }

    fun getText(): CharSequence? {
        return textView?.text
    }

    fun setHint(hint: CharSequence) {
        textView?.hint = hint
    }

    fun getHint(): CharSequence? {
        return textView?.hint
    }

}