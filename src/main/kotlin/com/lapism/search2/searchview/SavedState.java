package com.lapism.search2.searchview;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

import androidx.annotation.RequiresApi;

import com.google.android.libraries.material.opensearchbar.OpenSearchBar;


public class SavedState extends AbsSavedState {

    public static final Parcelable.Creator<OpenSearchBar.SavedState> CREATOR = new acuk();

    String f38994a;

    public SavedState(Parcel parcel, ClassLoader classLoader) {
        super(parcel, classLoader);
        this.f38994a = parcel.readString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f38994a);
    }

    public SavedState(Parcelable parcelable) {
        super(parcelable);
    }

}
