// File: ModifyScreen.kt
package com.example.lab3

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyScreen(navController: NavHostController, viewModel: MortgageViewModel) {
    // Use state hoisting for the amount and rate text fields
    val amountState = viewModel.amount.value.toString()
    val rateState = (viewModel.rate.value * 100).toString()  // Display rate as a percentage
    var amount by remember { mutableStateOf(amountState) }
    var rate by remember { mutableStateOf(rateState) }
    var selectedYears by remember { mutableStateOf(viewModel.years.value) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Modify Data") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            // Years Selector using Radio Buttons
            Text(text = "Years")
            Row {
                listOf(10, 15, 30).forEach { year ->
                    Row(
                        Modifier
                            .selectable(
                                selected = (year == selectedYears),
                                onClick = {
                                    selectedYears = year
                                }
                            )
                            .padding(horizontal = 8.dp)
                    ) {
                        RadioButton(
                            selected = (year == selectedYears),
                            onClick = null // RadioButton click is handled by Row's selectable modifier
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
                    viewModel.setYears(selectedYears)
                    viewModel.setAmount(amount.toFloatOrNull() ?: 0f) // Use safe call and fallback to 0f
                    viewModel.setRate(rate.toFloatOrNull()?.div(100) ?: 0f) // Convert percentage back to decimal
                    navController.popBackStack() // Navigate back
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "DONE")
            }
        }
    }
}
