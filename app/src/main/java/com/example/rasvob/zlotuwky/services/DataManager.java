package com.example.rasvob.zlotuwky.services;

import android.content.SharedPreferences;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import timber.log.Timber;

public class DataManager {

    private static String PREFS_OFFICE_RATE = "PREFS_OFFICE_RATE";
    private static String PREFS_VISA_RATE = "PREFS_VISA_RATE";

    private SharedPreferences sharedPreferences;
    private PageDownloadService pageDownloadService;

    public DataManager(SharedPreferences sharedPreferences, PageDownloadService pageDownloadService) {
        this.sharedPreferences = sharedPreferences;
        this.pageDownloadService = pageDownloadService;
    }

    public double getVisaExchangeRateFromNetwork() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        return pageDownloadService.getRate();
    }

    public double getVisaRate() {
        return getRate(PREFS_VISA_RATE);
    }

    public void saveVisaRate(double rate) {
        saveRate(PREFS_VISA_RATE, rate);
    }

    public double getOfficeRate() {
        return getRate(PREFS_OFFICE_RATE);
    }

    public void saveOfficeRate(double rate) {
        saveRate(PREFS_OFFICE_RATE, rate);
    }

    private void saveRate(String key, double value) {
        sharedPreferences.edit().putFloat(key, (float) value).apply();
    }

    private double getRate(String key) {
        return sharedPreferences.getFloat(key, 1.0f);
    }
}
