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

    public double getVisaExchangeRate() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        return pageDownloadService.getRate();
    }
}
