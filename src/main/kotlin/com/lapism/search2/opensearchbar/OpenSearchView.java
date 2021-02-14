package com.google.android.libraries.material.opensearchbar;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p003v7.widget.ActionMenuView;
import android.support.p003v7.widget.Toolbar;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.libraries.material.internal.ClippableRoundedCornerLayout;
import com.google.android.libraries.material.internal.TouchObserverFrameLayout;
import com.google.android.p012gm.R;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: PG */
public class OpenSearchView extends FrameLayout implements aje {

    /* renamed from: a */
    public final View f38996a;

    /* renamed from: b */
    public final ClippableRoundedCornerLayout f38997b;

    /* renamed from: c */
    final View f38998c;

    /* renamed from: d */
    final View f38999d;

    /* renamed from: e */
    public final FrameLayout f39000e;

    /* renamed from: f */
    public final Toolbar f39001f;

    /* renamed from: g */
    public final Toolbar f39002g;

    /* renamed from: h */
    public final TextView f39003h;

    /* renamed from: i */
    public final EditText f39004i;

    /* renamed from: j */
    public final ImageButton f39005j;

    /* renamed from: k */
    public final View f39006k;

    /* renamed from: l */
    public final TouchObserverFrameLayout f39007l;

    /* renamed from: m */
    public final Set<acvj> f39008m;

    /* renamed from: n */
    public OpenSearchBar f39009n;

    /* renamed from: o */
    public boolean f39010o;

    /* renamed from: p */
    public boolean f39011p;

    /* renamed from: q */
    public int f39012q;

    /* renamed from: r */
    private final boolean f39013r;

    /* renamed from: s */
    private final acvv f39014s;

    /* renamed from: t */
    private final ahtk f39015t;

    /* renamed from: u */
    private int f39016u;

    /* renamed from: v */
    private boolean f39017v;

    /* renamed from: w */
    private Map<View, Integer> f39018w;

    /* compiled from: PG */
    public class Behavior extends ajf<OpenSearchView> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        /* renamed from: e */
        public final /* bridge */ /* synthetic */ boolean mo931e(CoordinatorLayout coordinatorLayout, View view, View view2) {
            OpenSearchView openSearchView = (OpenSearchView) view;
            if (openSearchView.f39009n != null || !(view2 instanceof OpenSearchBar)) {
                return false;
            }
            openSearchView.mo21472c((OpenSearchBar) view2);
            return false;
        }
    }

    /* compiled from: PG */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new acvi();

        /* renamed from: a */
        String f39019a;

        /* renamed from: b */
        int f39020b;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f39019a = parcel.readString();
            this.f39020b = parcel.readInt();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.f39019a);
            parcel.writeInt(this.f39020b);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public OpenSearchView(Context context) {
        this(context, (AttributeSet) null);
    }

    /* renamed from: q */
    private final void m31791q() {
        float f;
        OpenSearchBar openSearchBar = this.f39009n;
        if (openSearchBar != null) {
            ahzo ahzo = openSearchBar.f38989v;
            f = ahzo != null ? ahzo.mo33903R() : C0371mv.m23792F(openSearchBar);
        } else {
            f = getResources().getDimension(R.dimen.google_opensearchview_elevation);
        }
        m31792r(f);
    }

    /* renamed from: r */
    private final void m31792r(float f) {
        ahtk ahtk = this.f39015t;
        if (ahtk != null && this.f38998c != null) {
            this.f38998c.setBackgroundColor(ahtk.mo33585a(f));
        }
    }

    /* renamed from: s */
    private final void m31793s() {
        int i;
        ImageButton b = ahwf.m53324b(this.f39001f);
        if (b != null) {
            if (this.f38997b.getVisibility() == 0) {
                i = 1;
            } else {
                i = 0;
            }
            Drawable a = C0306kp.m20758a(b.getDrawable());
            if (a instanceof C0505rt) {
                ((C0505rt) a).mo16041c((float) i);
            }
            if (a instanceof acts) {
                ((acts) a).mo18904a((float) i);
            }
        }
    }

    /* renamed from: t */
    private final void m31794t(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt != this) {
                if (childAt.findViewById(this.f38997b.getId()) != null) {
                    m31794t((ViewGroup) childAt, z);
                } else if (!z) {
                    Map<View, Integer> map = this.f39018w;
                    if (map != null && map.containsKey(childAt)) {
                        C0371mv.m23852m(childAt, this.f39018w.get(childAt).intValue());
                    }
                } else {
                    this.f39018w.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                    C0371mv.m23852m(childAt, 4);
                }
            }
        }
    }

    /* renamed from: a */
    public final ajf<OpenSearchView> mo926a() {
        return new Behavior();
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.f39013r) {
            this.f39007l.addView(view, i, layoutParams);
        } else {
            super.addView(view, i, layoutParams);
        }
    }

    /* renamed from: b */
    public final void mo21471b(int i) {
        if (this.f38999d.getLayoutParams().height != i) {
            this.f38999d.getLayoutParams().height = i;
            this.f38999d.requestLayout();
        }
    }

    /* renamed from: c */
    public final void mo21472c(OpenSearchBar openSearchBar) {
        this.f39009n = openSearchBar;
        this.f39014s.f33875e = openSearchBar;
        openSearchBar.setOnClickListener(new acvf(this));
        Toolbar toolbar = this.f39001f;
        if (toolbar != null && !(C0306kp.m20758a(toolbar.mo1686r()) instanceof C0505rt)) {
            OpenSearchBar openSearchBar2 = this.f39009n;
            if (openSearchBar2 == null) {
                this.f39001f.mo1684p(R.drawable.quantum_gm_ic_arrow_back_vd_theme_24);
            } else {
                this.f39001f.mo1685q(new acts(openSearchBar2.mo1686r(), C0492rg.m24749b(getContext(), R.drawable.quantum_gm_ic_arrow_back_vd_theme_24)));
                m31793s();
            }
        }
        m31791q();
    }

    /* renamed from: d */
    public final void mo21473d(acvj acvj) {
        this.f39008m.add(acvj);
    }

    /* renamed from: e */
    public final Editable mo21474e() {
        return this.f39004i.getText();
    }

    /* renamed from: f */
    public final void mo21475f() {
        this.f39004i.setText("");
    }

    /* renamed from: g */
    public final boolean mo21476g() {
        int i = this.f39012q;
        if (i != 0) {
            return i == 4 || i == 3;
        }
        throw null;
    }

    /* renamed from: h */
    public final void mo21477h() {
        int i = this.f39012q;
        if (i == 0) {
            throw null;
        } else if (i != 4 && i != 3) {
            acvv acvv = this.f39014s;
            if (acvv.f33875e != null) {
                if (acvv.f33871a.mo21482m()) {
                    acvv.f33871a.mo21479j();
                }
                acvv.f33871a.mo21484o(3);
                Menu t = acvv.f33873c.mo1688t();
                if (t != null) {
                    t.clear();
                }
                int i2 = acvv.f33875e.f38987t;
                if (i2 == -1 || !acvv.f33871a.f39011p) {
                    acvv.f33873c.setVisibility(8);
                } else {
                    acvv.f33873c.mo1691w(i2);
                    ActionMenuView a = ahwf.m53323a(acvv.f33873c);
                    if (a != null) {
                        for (int i3 = 0; i3 < a.getChildCount(); i3++) {
                            View childAt = a.getChildAt(i3);
                            childAt.setClickable(false);
                            childAt.setFocusable(false);
                            childAt.setFocusableInTouchMode(false);
                        }
                    }
                    acvv.f33873c.setVisibility(0);
                }
                acvv.f33874d.setText(acvv.f33875e.mo21458H());
                EditText editText = acvv.f33874d;
                editText.setSelection(editText.getText().length());
                acvv.f33872b.setVisibility(4);
                acvv.f33872b.post(new acvk(acvv));
            } else {
                if (acvv.f33871a.mo21482m()) {
                    OpenSearchView openSearchView = acvv.f33871a;
                    openSearchView.getClass();
                    openSearchView.postDelayed(new acvl(openSearchView), 150);
                }
                acvv.f33872b.setVisibility(4);
                acvv.f33872b.post(new acvm(acvv));
            }
            mo21483n(true);
        }
    }

    /* renamed from: i */
    public final void mo21478i() {
        int i = this.f39012q;
        if (i == 0) {
            throw null;
        } else if (i != 2 && i != 1) {
            acvv acvv = this.f39014s;
            if (acvv.f33875e != null) {
                if (acvv.f33871a.mo21482m()) {
                    acvv.f33871a.mo21480k();
                }
                AnimatorSet b = acvv.mo18969b(false);
                b.addListener(new acvr(acvv));
                b.start();
            } else {
                if (acvv.f33871a.mo21482m()) {
                    acvv.f33871a.mo21480k();
                }
                AnimatorSet a = acvv.mo18968a(false);
                a.addListener(new acvt(acvv));
                a.start();
            }
            mo21483n(false);
        }
    }

    /* renamed from: j */
    public final void mo21479j() {
        if (this.f39017v) {
            this.f39004i.post(new acvg(this));
        }
    }

    /* renamed from: k */
    public final void mo21480k() {
        this.f39004i.clearFocus();
        mo21481l().hideSoftInputFromWindow(this.f39004i.getWindowToken(), 0);
    }

    /* renamed from: l */
    public final InputMethodManager mo21481l() {
        return (InputMethodManager) getContext().getSystemService("input_method");
    }

    /* renamed from: m */
    public final boolean mo21482m() {
        return this.f39016u == 48;
    }

    /* renamed from: n */
    public final void mo21483n(boolean z) {
        ViewGroup viewGroup = (ViewGroup) getRootView();
        if (z) {
            this.f39018w = new HashMap(viewGroup.getChildCount());
        }
        m31794t(viewGroup, z);
        if (!z) {
            this.f39018w = null;
        }
    }

    /* renamed from: o */
    public final void mo21484o(int i) {
        int i2 = this.f39012q;
        if (i2 == 0) {
            throw null;
        } else if (i2 != i) {
            this.f39012q = i;
            for (acvj a : new LinkedHashSet(this.f39008m)) {
                a.mo10067a(i2, i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ahzp.m53569e(this);
    }

    /* access modifiers changed from: protected */
    public final void onFinishInflate() {
        boolean z;
        super.onFinishInflate();
        Activity a = ahuw.m53266a(getContext());
        if (a != null) {
            Window window = a.getWindow();
            if (window != null) {
                this.f39016u = window.getAttributes().softInputMode;
            }
            int i = 0;
            if (window == null) {
                z = false;
            } else {
                WindowManager.LayoutParams attributes = window.getAttributes();
                z = (attributes.flags & 67108864) == 67108864 || (attributes.flags & 512) == 512 || (window.getDecorView().getSystemUiVisibility() & 768) == 768;
            }
            View view = this.f38999d;
            if (true != z) {
                i = 8;
            }
            view.setVisibility(i);
        }
    }

    /* access modifiers changed from: protected */
    public final void onRestoreInstanceState(Parcelable parcelable) {
        boolean z;
        int i;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1751d);
        this.f39004i.setText(savedState.f39019a);
        int i2 = savedState.f39020b;
        boolean z2 = true;
        int i3 = 0;
        if (i2 == 0) {
            z = true;
        } else {
            z = false;
        }
        if (this.f38997b.getVisibility() != 0) {
            z2 = false;
        }
        ClippableRoundedCornerLayout clippableRoundedCornerLayout = this.f38997b;
        if (i2 != 0) {
            i3 = 8;
        }
        clippableRoundedCornerLayout.setVisibility(i3);
        m31793s();
        if (z2 != z) {
            mo21483n(z);
        }
        if (i2 == 0) {
            i = 4;
        } else {
            i = 2;
        }
        mo21484o(i);
    }

    /* access modifiers changed from: protected */
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Editable e = mo21474e();
        savedState.f39019a = e == null ? null : e.toString();
        savedState.f39020b = this.f38997b.getVisibility();
        return savedState;
    }

    /* renamed from: p */
    public final void mo21489p() {
        this.f39010o = false;
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        m31792r(f);
    }

    public OpenSearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.openSearchViewStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OpenSearchView(android.content.Context r18, android.util.AttributeSet r19, int r20) {
        /*
            r17 = this;
            r0 = r17
            r2 = r19
            r4 = r20
            r1 = 2132019083(0x7f14078b, float:1.967649E38)
            r3 = r18
            android.content.Context r1 = p000.aief.m53753a(r3, r2, r4, r1)
            r0.<init>(r1, r2, r4)
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.f39008m = r1
            r1 = 16
            r0.f39016u = r1
            r7 = 2
            r0.f39012q = r7
            android.content.Context r8 = r17.getContext()
            int[] r3 = p000.acvw.f33884b
            r9 = 0
            int[] r6 = new int[r9]
            r5 = 2132019083(0x7f14078b, float:1.967649E38)
            r1 = r8
            android.content.res.TypedArray r1 = p000.ahwe.m53316a(r1, r2, r3, r4, r5, r6)
            r2 = -1
            int r3 = r1.getResourceId(r9, r2)
            r4 = 1
            java.lang.String r5 = r1.getString(r4)
            java.lang.String r6 = r1.getString(r7)
            r7 = 7
            java.lang.String r7 = r1.getString(r7)
            r10 = 8
            boolean r11 = r1.getBoolean(r10, r9)
            r12 = 4
            boolean r12 = r1.getBoolean(r12, r4)
            r0.f39010o = r12
            r12 = 3
            boolean r12 = r1.getBoolean(r12, r4)
            r0.f39011p = r12
            r12 = 6
            boolean r12 = r1.getBoolean(r12, r9)
            r13 = 5
            boolean r13 = r1.getBoolean(r13, r4)
            r0.f39017v = r13
            r1.recycle()
            android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r8)
            r13 = 2131624759(0x7f0e0337, float:1.8876707E38)
            r1.inflate(r13, r0)
            r0.f39013r = r4
            r1 = 2131429645(0x7f0b090d, float:1.8480969E38)
            android.view.View r1 = r0.findViewById(r1)
            r0.f38996a = r1
            r1 = 2131429644(0x7f0b090c, float:1.8480967E38)
            android.view.View r1 = r0.findViewById(r1)
            com.google.android.libraries.material.internal.ClippableRoundedCornerLayout r1 = (com.google.android.libraries.material.internal.ClippableRoundedCornerLayout) r1
            r0.f38997b = r1
            r13 = 2131429637(0x7f0b0905, float:1.8480952E38)
            android.view.View r13 = r0.findViewById(r13)
            r0.f38998c = r13
            r13 = 2131429647(0x7f0b090f, float:1.8480973E38)
            android.view.View r13 = r0.findViewById(r13)
            r0.f38999d = r13
            r14 = 2131429649(0x7f0b0911, float:1.8480977E38)
            android.view.View r14 = r0.findViewById(r14)
            android.widget.FrameLayout r14 = (android.widget.FrameLayout) r14
            r0.f39000e = r14
            r14 = 2131429648(0x7f0b0910, float:1.8480975E38)
            android.view.View r14 = r0.findViewById(r14)
            android.support.v7.widget.Toolbar r14 = (android.support.p003v7.widget.Toolbar) r14
            r0.f39001f = r14
            r15 = 2131429641(0x7f0b0909, float:1.848096E38)
            android.view.View r15 = r0.findViewById(r15)
            android.support.v7.widget.Toolbar r15 = (android.support.p003v7.widget.Toolbar) r15
            r0.f39002g = r15
            r15 = 2131429646(0x7f0b090e, float:1.848097E38)
            android.view.View r15 = r0.findViewById(r15)
            android.widget.TextView r15 = (android.widget.TextView) r15
            r0.f39003h = r15
            r9 = 2131429642(0x7f0b090a, float:1.8480962E38)
            android.view.View r9 = r0.findViewById(r9)
            android.widget.EditText r9 = (android.widget.EditText) r9
            r0.f39004i = r9
            r10 = 2131429638(0x7f0b0906, float:1.8480954E38)
            android.view.View r10 = r0.findViewById(r10)
            android.widget.ImageButton r10 = (android.widget.ImageButton) r10
            r0.f39005j = r10
            r2 = 2131429640(0x7f0b0908, float:1.8480958E38)
            android.view.View r2 = r0.findViewById(r2)
            r0.f39006k = r2
            r4 = 2131429639(0x7f0b0907, float:1.8480956E38)
            android.view.View r4 = r0.findViewById(r4)
            com.google.android.libraries.material.internal.TouchObserverFrameLayout r4 = (com.google.android.libraries.material.internal.TouchObserverFrameLayout) r4
            r0.f39007l = r4
            r16 = r13
            acvv r13 = new acvv
            r13.<init>(r0)
            r0.f39014s = r13
            ahtk r13 = new ahtk
            r13.<init>(r8)
            r0.f39015t = r13
            android.view.View$OnTouchListener r8 = p000.acuy.f33845a
            r1.setOnTouchListener(r8)
            r17.m31791q()
            r15.setText(r7)
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            r7 = 1
            if (r7 == r1) goto L_0x0113
            r1 = 0
            goto L_0x0115
        L_0x0113:
            r1 = 8
        L_0x0115:
            r15.setVisibility(r1)
            r1 = -1
            if (r3 == r1) goto L_0x011e
            r9.setTextAppearance(r3)
        L_0x011e:
            r9.setText(r5)
            r9.setHint(r6)
            r1 = 2130968824(0x7f0400f8, float:1.7546313E38)
            if (r12 == 0) goto L_0x012f
            r3 = 0
            r14.mo1685q(r3)
            goto L_0x014c
        L_0x012f:
            acuz r3 = new acuz
            r3.<init>(r0)
            r14.mo1687s(r3)
            if (r11 == 0) goto L_0x014c
            rt r3 = new rt
            android.content.Context r5 = r17.getContext()
            r3.<init>(r5)
            int r5 = p000.ahyw.m53497c(r0, r1)
            r3.mo16039a(r5)
            r14.mo1685q(r3)
        L_0x014c:
            acva r3 = new acva
            r3.<init>(r0)
            r10.setOnClickListener(r3)
            acvh r3 = new acvh
            r3.<init>(r0)
            r9.addTextChangedListener(r3)
            int r1 = p000.ahyw.m53497c(r0, r1)
            r3 = 1106562252(0x41f4cccc, float:30.599998)
            int r3 = java.lang.Math.round(r3)
            int r1 = p000.C0288jy.m19718b(r1, r3)
            r2.setBackgroundColor(r1)
            acvb r1 = new acvb
            r1.<init>(r0)
            r4.f38981a = r1
            acvc r1 = new acvc
            r1.<init>(r0)
            p000.ahwl.m53331e(r14, r1)
            android.view.ViewGroup$LayoutParams r1 = r2.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r1 = (android.view.ViewGroup.MarginLayoutParams) r1
            int r3 = r1.leftMargin
            int r4 = r1.rightMargin
            acve r5 = new acve
            r5.<init>(r1, r3, r4)
            p000.C0371mv.m23799M(r2, r5)
            android.content.res.Resources r1 = r17.getResources()
            java.lang.String r2 = "status_bar_height"
            java.lang.String r3 = "dimen"
            java.lang.String r4 = "android"
            int r1 = r1.getIdentifier(r2, r3, r4)
            if (r1 <= 0) goto L_0x01a8
            android.content.res.Resources r2 = r17.getResources()
            int r9 = r2.getDimensionPixelSize(r1)
            goto L_0x01a9
        L_0x01a8:
            r9 = 0
        L_0x01a9:
            r0.mo21471b(r9)
            acvd r1 = new acvd
            r1.<init>(r0)
            r2 = r16
            p000.C0371mv.m23799M(r2, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.material.opensearchbar.OpenSearchView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }
}
