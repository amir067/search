package com.lapism.search2.widget

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.AttrRes
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import com.lapism.search.R


@Suppress("unused")
class MaterialSearchToolbar : MaterialToolbar {

    private var textView: MaterialTextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.material_search_toolbar, this)

        //MaterialColors.getColor()
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
        info?.className = MaterialTextView::class.java.canonicalName
        var h: CharSequence? = getText()
        val isEmpty = TextUtils.isEmpty(h)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            info?.hintText = getTextHint()
            info?.isShowingHintText = isEmpty
        }
        if (isEmpty) {
            h = getTextHint()
        }
        info?.text = h
    }

    fun setText(text: CharSequence?) {
        textView?.text = text
    }

    fun getText(): CharSequence? {
        return textView?.text
    }

    fun setTextHint(hint: CharSequence?) {
        textView?.hint = hint
    }

    fun getTextHint(): CharSequence? {
        return textView?.hint
    }

}

/*    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f40605v && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            Resources resources = getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.google_opensearchbar_margin_horizontal);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.google_opensearchbar_margin_vertical);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            int i = marginLayoutParams.leftMargin;
            if (i == 0) {
                i = dimensionPixelSize;
            }
            marginLayoutParams.leftMargin = i;
            int i2 = marginLayoutParams.topMargin;
            if (i2 == 0) {
                i2 = dimensionPixelSize2;
            }
            marginLayoutParams.topMargin = i2;
            int i3 = marginLayoutParams.rightMargin;
            if (i3 != 0) {
                dimensionPixelSize = i3;
            }
            marginLayoutParams.rightMargin = dimensionPixelSize;
            int i4 = marginLayoutParams.bottomMargin;
            if (i4 != 0) {
                dimensionPixelSize2 = i4;
            }
            marginLayoutParams.bottomMargin = dimensionPixelSize2;
        }
    }*/

/*    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EditText.class.getCanonicalName());
        CharSequence text = this.f40599p.getText();
        boolean isEmpty = TextUtils.isEmpty(text);
        if (Build.VERSION.SDK_INT >= 26) {
            accessibilityNodeInfo.setHintText(this.f40599p.getHint());
            accessibilityNodeInfo.setShowingHintText(isEmpty);
        }
        if (isEmpty) {
            text = this.f40599p.getHint();
        }
        accessibilityNodeInfo.setText(text);
    }*/