package com.example.rasvob.zlotuwky.services;

public class ExchangeConverter {
    public double convert(double amount, double rate) {
        return (amount/rate) * 100;
    }
}
