package com.example.rasvob.zlotuwky.activities.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.rasvob.zlotuwky.helpers.SingleLiveEvent;
import com.example.rasvob.zlotuwky.services.DataManager;
import com.example.rasvob.zlotuwky.services.ExchangeConvertor;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<Double> officeRate = new MutableLiveData<>();
    private MutableLiveData<Double> visaRate = new MutableLiveData<>();

    private MutableLiveData<Double> officeAmount = new MutableLiveData<>();
    private MutableLiveData<Double> visaAmount = new MutableLiveData<>();
    private MutableLiveData<Double> convertAmount = new MutableLiveData<>();
    private DataManager dataManager;
    private ExchangeConvertor convertor;

    private SingleLiveEvent<String> onError = new SingleLiveEvent<>();
    private SingleLiveEvent<String> showProgress = new SingleLiveEvent<>();
    private SingleLiveEvent hideProgress = new SingleLiveEvent();

    public MainActivityViewModel(DataManager dataManager, ExchangeConvertor convertor) {
        this.dataManager = dataManager;
        this.convertor = convertor;
    }

    public boolean setRate(MutableLiveData<Double> data, String rate) {
        if (!validateAmountOrRate(rate)) return false;
        data.setValue(Double.parseDouble(rate));
        recalculateValues();
        return true;
    }

    public boolean validateAmountOrRate(String value) {
        return (value != null) && !value.isEmpty() && value.matches("\\d+(?:\\.\\d+)?");
    }

    public void recalculateValues() {
        Double convertAmount = this.convertAmount.getValue();
        Double visaRate = this.visaRate.getValue();
        Double officeRate = this.officeRate.getValue();

        if (convertAmount == null || visaRate == null || officeRate == null) {
            return;
        }

        double visa = convertor.convert(convertAmount, visaRate);
        double office = convertor.convert(convertAmount, officeRate);
        visaAmount.setValue(visa);
        officeAmount.setValue(office);
    }

    public MutableLiveData<Double> getOfficeRate() {
        return officeRate;
    }

    public MutableLiveData<Double> getVisaRate() {
        return visaRate;
    }

    public MutableLiveData<Double> getOfficeAmount() {
        return officeAmount;
    }

    public MutableLiveData<Double> getVisaAmount() {
        return visaAmount;
    }

    public MutableLiveData<Double> getConvertAmount() {
        return convertAmount;
    }

    public void downloadVisaExchangeRate() {
        showProgress.call();
        Single<Double> single = Single.create(emitter -> {
            try {
                double rate = dataManager.getVisaExchangeRate();
                if (rate > 0) {
                    emitter.onSuccess(rate);
                    return;
                }
                emitter.onError(new IOException());
            } catch (IOException e) {
                Timber.e(e);

            } catch (Exception e) {
                Timber.e(e);
                emitter.onError(new Exception("Kurz nebylo možné stáhnout"));
            }
        });

        Disposable disposable = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Double res) -> {
                    getVisaRate().setValue(100.0/res);
                    recalculateValues();
                    hideProgress.call();
                }, (Throwable err) -> {
                    getOnError().setValue(err.getMessage());
                    hideProgress.call();
                });
    }

    public SingleLiveEvent<String> getOnError() {
        return onError;
    }

    public SingleLiveEvent<String> getShowProgress() {
        return showProgress;
    }

    public SingleLiveEvent getHideProgress() {
        return hideProgress;
    }
}
