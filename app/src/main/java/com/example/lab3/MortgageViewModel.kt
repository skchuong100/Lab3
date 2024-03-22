package com.example.lab3

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class MortgageViewModel : ViewModel() {
    // The Mortgage instance
    private val mortgage = Mortgage()

    // Observable state properties
    val amount = mutableFloatStateOf(mortgage.amount)
    val years = mutableIntStateOf(mortgage.years)
    val rate = mutableFloatStateOf(mortgage.rate)

    // Expose formatted strings as state properties
    val formattedAmount: String
        get() = mortgage.formattedAmount

    val formattedMonthlyPayment: String
        get() = mortgage.formattedMonthlyPayment()

    val formattedTotalPayment: String
        get() = mortgage.formattedTotalPayment()

    // Functions to modify the mortgage details
    fun setAmount(newAmount: Float) {
        if (newAmount >= 0) {
            mortgage.amount = newAmount
            amount.floatValue = newAmount // Update the observable state
            Log.d("amount", "set amount: ${mortgage.amount}" )

        }
    }

    fun setYears(newYears: Int) {
        if (newYears >= 0) {
            mortgage.years = newYears
            years.intValue = newYears // Update the observable state
            Log.d("year", "set year: ${mortgage.years}" )

        }
    }

    // Make sure to pass in the rate as a decimal value, so if input is in percentage, divide by 100 when setting
    fun setRate(newRate: Float) {
        if (newRate >= 0) {
            val rateAsDecimal = newRate
            mortgage.rate = rateAsDecimal
            rate.floatValue = rateAsDecimal // Update the observable state
            Log.d("rate", "set rate: ${mortgage.rate}" )

        }
    }
}
