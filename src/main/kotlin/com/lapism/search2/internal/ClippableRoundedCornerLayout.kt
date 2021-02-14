package com.lapism.search2.internal

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout


class ClippableRoundedCornerLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var path: Path? = null

    override fun dispatchDraw(canvas: Canvas?) {
        if (path == null) {
            super.dispatchDraw(canvas)
            return
        }
        val save = canvas?.save()
        canvas?.clipPath(path!!)
        super.dispatchDraw(canvas)
        canvas?.restoreToCount(save!!)
    }

}