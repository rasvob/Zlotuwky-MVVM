package com.example.rasvob.zlotuwky.view.fab_container.components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasvob.zlotuwky.R;

public abstract class AbstractExchangeInfo extends ConstraintLayout {
    protected TextView amount;
    protected TextView rate;
    protected ImageView tick;

    public AbstractExchangeInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTickImg(boolean fullCircle) {
        int img = fullCircle ? R.drawable.ic_baseline_check_circle_24px : R.drawable.ic_baseline_check_circle_outline_24px;
        tick.setImageDrawable(ContextCompat.getDrawable(getContext(), img));
        invalidate();
        requestLayout();
    }

    public void setAmount(String value) {
        setText(amount, value);
    }

    public void setRate(String value) {
        setText(rate, value);
    }

    private void setText(TextView view, String text) {
        view.setText(text);
        invalidate();
        requestLayout();
    }
}
