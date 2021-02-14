package com.google.android.libraries.material.opensearchbar;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.appbar.AppBarLayout;

public class OpenSearchBar extends Toolbar {

    private final View.OnClickListener f38982A;
    private Integer f38983B;
    public final TextView f38984q;
    public final CardView f38985r;
    public View f38986s;
    public int f38987t;
    public boolean f38988u;
    public ahzo f38989v;
    private final boolean f38990w;
    private final boolean f38991x;
    private final Drawable f38992y;
    private final boolean f38993z;


    public class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {

        private boolean f38995f = false;

        /* renamed from: e */
        public final boolean mo931e(CoordinatorLayout coordinatorLayout, View view, View view2) {
            super.mo931e(coordinatorLayout, view, view2);
            if (!this.f38995f && (view2 instanceof AppBarLayout)) {
                this.f38995f = true;
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                appBarLayout.setBackgroundColor(0);
                ahpj.m52916a(appBarLayout, 0.0f);
            }
            return false;
        }

        /* renamed from: g */
        public final /* bridge */ /* synthetic */ boolean mo933g(CoordinatorLayout coordinatorLayout, View view, int i) {
            super.mo933g(coordinatorLayout, view, i);
            return true;
        }

        /* renamed from: s */
        public final boolean mo21469s() {
            return true;
        }
    }



    /* renamed from: O */
    private void m31773O(int i) {
        ImageButton b = ahwf.m53324b(this);
        if (b != null) {
            C0371mv.m23852m(b, i);
        }
    }

    /* renamed from: P */
    private static int m31774P(int i, int i2) {
        return i == 0 ? i2 : i;
    }

    /* renamed from: G */
    public final void mo21457G() {
        if (getLayoutParams() instanceof ahpc) {
            ahpc ahpc = (ahpc) getLayoutParams();
            if (this.f38988u) {
                if (ahpc.f66174a == 0) {
                    ahpc.f66174a = 53;
                }
            } else if (ahpc.f66174a == 53) {
                ahpc.f66174a = 0;
            }
        }
    }

    /* renamed from: H */
    public final CharSequence mo21458H() {
        return this.f38984q.getText();
    }

    /* renamed from: I */
    public final void mo21459I(CharSequence charSequence) {
        this.f38984q.setText(charSequence);
    }


    /* renamed from: L */
/*    public final void mo21462L() {
        acux acux = this.f38985r;
        Animator animator = acux.f33839a;
        if (animator != null) {
            animator.end();
        }
        Animator animator2 = acux.f33840b;
        if (animator2 != null) {
            animator2.end();
        }
        View view = this.f38986s;
        if (view instanceof acrl) {
            ((acrl) view).mo5486k();
        }
        if (view != null) {
            view.setAlpha(0.0f);
        }
    }*/

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.f38990w && this.f38986s == null && !(view instanceof ActionMenuView)) {
            this.f38986s = view;
            view.setAlpha(0.0f);
        }
        super.addView(view, i, layoutParams);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ahzp.m53570f(this, this.f38989v);
        if (this.f38991x && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            Resources resources = getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.google_opensearchbar_margin_horizontal);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.google_opensearchbar_margin_vertical);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            marginLayoutParams.leftMargin = m31774P(marginLayoutParams.leftMargin, dimensionPixelSize);
            marginLayoutParams.topMargin = m31774P(marginLayoutParams.topMargin, dimensionPixelSize2);
            marginLayoutParams.rightMargin = m31774P(marginLayoutParams.rightMargin, dimensionPixelSize);
            marginLayoutParams.bottomMargin = m31774P(marginLayoutParams.bottomMargin, dimensionPixelSize2);
        }
        mo21457G();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View view = this.f38986s;
        if (view != null) {
            int measuredWidth = view.getMeasuredWidth();
            int measuredWidth2 = (getMeasuredWidth() / 2) - (measuredWidth / 2);
            int i5 = measuredWidth + measuredWidth2;
            int measuredHeight = this.f38986s.getMeasuredHeight();
            int measuredHeight2 = (getMeasuredHeight() / 2) - (measuredHeight / 2);
            int i6 = measuredHeight + measuredHeight2;
            View view2 = this.f38986s;
            if (C0371mv.m23858s(this) == 1) {
                view2.layout(getMeasuredWidth() - i5, measuredHeight2, getMeasuredWidth() - measuredWidth2, i6);
            } else {
                view2.layout(measuredWidth2, measuredHeight2, i5, i6);
            }
        }
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        View view = this.f38986s;
        if (view != null) {
            view.measure(i, i2);
        }
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1751d);
        mo21459I(savedState.f38994a);
    }

    /* access modifiers changed from: protected */
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence H = mo21458H();
        savedState.f38994a = H == null ? null : H.toString();
        return savedState;
    }

    /* renamed from: s */
    public final void mo1687s(View.OnClickListener onClickListener) {
        super.mo1687s(onClickListener);
        if (onClickListener != this.f38982A) {
            m31773O(0);
        }
    }

    public OpenSearchBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public OpenSearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.openSearchBarStyle);
    }

    public OpenSearchBar(android.content.Context r17, android.util.AttributeSet r18, int r19) {
    }
}
