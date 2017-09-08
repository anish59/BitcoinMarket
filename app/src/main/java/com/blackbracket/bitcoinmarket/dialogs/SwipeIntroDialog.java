package com.blackbracket.bitcoinmarket.dialogs;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackbracket.bitcoinmarket.R;

/**
 * Created by anish on 08-09-2017.
 */

public class SwipeIntroDialog extends Dialog {
    private Context context;
    private ValueAnimator objectAnimator;
    private android.widget.ImageView imgSwipeDown;
    private View view;
    private android.widget.TextView txtOk;
    private OnIntroDismissListener onIntroDismissListener;

    public SwipeIntroDialog(@NonNull Context context, OnIntroDismissListener onIntroDismissListener) {
        super(context);
        this.context = context;
        this.onIntroDismissListener = onIntroDismissListener;
        setProp();
    }

    private void setProp() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_swipe_intro, null, false);
        txtOk = (TextView) view.findViewById(R.id.txtOk);
        imgSwipeDown = (ImageView) view.findViewById(R.id.imgSwipeDown);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        show();
        animateObject();
        setListeners();
    }

    private void setListeners() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIntroDismissListener.onDismissed();
                dismiss();
            }
        });
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onIntroDismissListener.onDismissed();
                dismiss();
            }
        });
    }

    private void animateObject() {
        objectAnimator = ObjectAnimator.ofFloat(imgSwipeDown, "translationY", 0, 100);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public interface OnIntroDismissListener {
        void onDismissed();
    }
}
