package com.lapism.search.widget

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.View


internal class SavedState(superState: Parcelable) : View.BaseSavedState(superState) {
    /*    companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }*/
    // *********************************************************************************************
    var query: CharSequence? = null
    var hasFocus: Boolean = false

    // *********************************************************************************************
    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        TextUtils.writeToParcel(query, out, flags)
        out.writeInt(if (hasFocus) 1 else 0)
    }

}
