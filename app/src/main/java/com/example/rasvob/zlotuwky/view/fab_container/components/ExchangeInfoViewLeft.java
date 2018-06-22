package com.example.rasvob.zlotuwky.view.fab_container.components;

import android.content.Context;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasvob.zlotuwky.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExchangeInfoViewLeft extends AbstractExchangeInfo {
    public ExchangeInfoViewLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_exchange_info_left, this);
        amount = (TextView) findViewById(R.id.exchange_view_amount_visa);
        rate = (TextView) findViewById(R.id.exchange_view_rate_visa);
        tick = (ImageView) findViewById(R.id.exchange_view_tick_img_visa);
    }
}
