package com.example.rasvob.zlotuwky.view.fab_container.components;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasvob.zlotuwky.R;

public class ExchangeInfoViewRight extends AbstractExchangeInfo {

    public ExchangeInfoViewRight(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_exchange_info_right, this);
        amount = (TextView) findViewById(R.id.exchange_view_amount_office);
        rate = (TextView) findViewById(R.id.exchange_view_rate_office);
        tick = (ImageView) findViewById(R.id.exchange_view_tick_img_office);
    }
}
