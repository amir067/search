package com.lapism.search2.internal

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout


class TouchObserverFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var listener: OnTouchListener? = null

    override fun setOnTouchListener(l: OnTouchListener?) {
        listener = l
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val onTouchListener = listener
        onTouchListener?.onTouch(this, ev)
        return super.onInterceptTouchEvent(ev)
    }

}