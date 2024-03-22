package com.example.lab3;

import java.text.DecimalFormat;

public class Mortgage {
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("$#,##0.00");

    private float amount;
    private int years;
    private float rate;

    public Mortgage() {
        setAmount(0.0f); // Default amount
        setYears(10); // Default years
        setRate(0.0f); // Default interest rate
    }

    public void setAmount(float newAmount) {
        if (newAmount >= 0) amount = newAmount;
    }

    public void setYears(int newYears) {
        if (newYears >= 0) years = newYears;
    }

    public void setRate(float newRate) {
        if (newRate >= 0) rate = newRate;
    }

    public float getAmount() {
        return amount;
    }

    public String getFormattedAmount() {
        return MONEY_FORMAT.format(amount);
    }

    public int getYears() {
        return years;
    }

    public float getRate() {
        return rate;
    }

    public float monthlyPayment() {
        if (rate > 0) {
            float monthlyRate = rate / 12; // Convert annual rate to monthly
            int totalPayments = years * 12; // Total number of payments
            // Apply the formula
            double temp = Math.pow(1 + monthlyRate, totalPayments);
            return (float) (amount * monthlyRate / (temp - 1) * temp); // Cast the result to float
        } else {
            // If the interest rate is 0, it's a simple division
            return amount / (years * 12);
        }
    }



    public String formattedMonthlyPayment() {
        return MONEY_FORMAT.format(monthlyPayment());
    }

    public float totalPayment() {
        return monthlyPayment() * years * 12;
    }

    public String formattedTotalPayment() {
        return MONEY_FORMAT.format(totalPayment());
    }
}
