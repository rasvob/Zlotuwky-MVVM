package com.example.rasvob.zlotuwky.activities.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rasvob.zlotuwky.R;
import com.example.rasvob.zlotuwky.fragments.BottomSheetFragment;
import com.example.rasvob.zlotuwky.fragments.SetOwnRateFragmentDialog;
import com.example.rasvob.zlotuwky.services.PageDownloadService;
import com.example.rasvob.zlotuwky.view.fab_container.components.AbstractExchangeInfo;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    //VM and services
    MainActivityViewModel vm;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    PageDownloadService service;

    @Inject
    MainActivityViewModelFactory viewModelFactory;

    //View binding
    @BindView(R.id.view_visa_exchange)
    AbstractExchangeInfo visaInfo;

    @BindView(R.id.view_office_exchange)
    AbstractExchangeInfo officeInfo;

    @BindView(R.id.coordinator_main)
    CoordinatorLayout root;

    @BindView(R.id.progressBar_rate_caption)
    TextView progressCaption;

    @BindView(R.id.progressBar_rate)
    ProgressBar progressBar;

    @BindView(R.id.input_money_amount)
    EditText amount;

    private void onOfficeRateChanged(Double val) {
        officeInfo.setRate(String.format(Locale.ENGLISH,"%.2f", val));
        setTick();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressCaption.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        progressCaption.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vm = ViewModelProviders.of(this, viewModelFactory)
                .get(MainActivityViewModel.class);

        vm.getOfficeRate().observe(this, this::onOfficeRateChanged);
        vm.getVisaRate().observe(this, this::onVisaRateChanged);
        vm.getOnError().observe(this, this::OnErrorEvent);
        vm.getShowProgress().observe(this, s -> showProgress());
        vm.getHideProgress().observe(this, o -> hideProgress());
        vm.getOfficeAmount().observe(this, this::onOfficeAmountChanged);
        vm.getVisaAmount().observe(this, this::onVisaAmountChanged);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void onOfficeAmountChanged(Double val) {
        officeInfo.setAmount(String.format(Locale.ENGLISH,"%.3f", val));
    }

    private void onVisaAmountChanged(Double val) {
        visaInfo.setAmount(String.format(Locale.ENGLISH,"%.3f", val));
    }

    private void setTick() {
        Double officeVal = vm.getOfficeRate().getValue();
        Double visaVal = vm.getVisaRate().getValue();

        if (officeVal == null || visaVal == null) {
            return;
        }

        boolean res = visaVal >= officeVal;
        officeInfo.setTickImg(!res);
        visaInfo.setTickImg(res);
    }

    private void onVisaRateChanged(Double val) {
        visaInfo.setRate(String.format(Locale.ENGLISH,"%.2f", val));
        setTick();
    }

    private void OnErrorEvent(String err) {
        Snackbar.make(root, err, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void openBottomSheet() {
        BottomSheetFragment fragment = new BottomSheetFragment();
        fragment.setOfficeCallback(this::setOwnRateCallback);
        fragment.setVisaCallback(this::downloadVisaCallback);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    @OnTextChanged(R.id.input_money_amount)
    public void amountInputChanged() {
        String amountStr = amount.getText().toString();
        double val = vm.validateAmountOrRate(amountStr) ? Double.parseDouble(amountStr) : 0.0;
        vm.getConvertAmount().setValue(val);
        vm.recalculateValues();
    }

    public void downloadVisaCallback() {
        vm.downloadVisaExchangeRate();
    }

    public void setOwnRateCallback() {
        SetOwnRateFragmentDialog dialog = new SetOwnRateFragmentDialog();
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
