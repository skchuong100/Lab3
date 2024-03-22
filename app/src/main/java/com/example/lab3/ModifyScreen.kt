// File: ModifyScreen.kt
package com.example.lab3

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ModifyScreen(navController: NavHostController, viewModel: MortgageViewModel) {
    val amountState = viewModel.amount.floatValue
    val rateState = viewModel.rate.floatValue * 100  // Assuming rate is in decimal form and we want to display it as a percentage

    var amount by remember { mutableStateOf(amountState.toString()) }
    var rate by remember { mutableStateOf(rateState.toString()) }
    var selectedYears by remember { mutableIntStateOf(viewModel.years.intValue) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Modify Data", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Years Selector using Radio Buttons
        Text(text = "Years")
        Row {
            listOf(10, 15, 30).forEach { year ->
                Row(
                    Modifier
                        .selectable(
                            selected = (year == selectedYears), // Use the .value for comparison
                            onClick = {
                                selectedYears = year // Assign the new value to the state
                                viewModel.setYears(year) // Update the ViewModel with the new year
                            }
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    RadioButton(
                        selected = (year == selectedYears), // Use the .value for comparison
                        onClick = null // We handle the click in the Modifier.selectable
                    )
                    Text(
                        text = "$year",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Amount Input
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Rate Input
        OutlinedTextField(
            value = rate,
            onValueChange = { rate = it },
            label = { Text("Interest Rate (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Done Button
        Button(
            onClick = {
                viewModel.setAmount(amount.toFloatOrNull() ?: 0f) // Use safe call and fallback to 0f
                viewModel.setRate(rate.toFloatOrNull()?.div(100) ?: 0f) // Convert percentage back to decimal
                navController.popBackStack() // Navigate back
                Log.d("amount", "set amount: ${viewModel.formattedAmount}" )
                Log.d("year", "set year: ${viewModel.years.intValue}" )
                Log.d("rate", "set rate: ${viewModel.rate.floatValue * 100}%" )



            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "DONE")
        }
    }
}
