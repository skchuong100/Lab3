package com.example.lab3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab3.ui.theme.Lab3Theme
import com.example.lab3.ModifyScreen
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MortgageApp()
                }
            }
        }
    }
}

@Composable
fun MortgageApp() {
    val navController = rememberNavController()
    AppNavGraph(navController)
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    val viewModel: MortgageViewModel = viewModel() // Get the ViewModel here

    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController, viewModel) // Pass the ViewModel to the MainScreen
        }
        composable("modify") {
            // Pass the same ViewModel instance to the ModifyScreen
            ModifyScreen(navController, viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: MortgageViewModel = viewModel()) {
    val appBarColor = MaterialTheme.colorScheme.primaryContainer
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mortgage Calculator") },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = appBarColor)
                // Actions and navigation icon can be added here if needed
            )
        }
    ) { paddingValues -> // Padding provided by the Scaffold to account for the TopAppBar
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = "Amount: ${viewModel.formattedAmount}")
            Text(text = "Years: ${viewModel.years.value}") // Removed the incorrect intValue usage
            Text(text = "Interest Rate: ${"%.2f".format(viewModel.rate.value * 100)}%")
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                thickness = 2.dp, // Set the thickness of the divider line
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(text = "Monthly Payment: ${viewModel.formattedMonthlyPayment}")
            Text(text = "Total Payment: ${viewModel.formattedTotalPayment}")
            Button(
                onClick = { navController.navigate("modify") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "MODIFY DATA")
            }
        }
    }
}







// Implement the MortgageViewModel class
// Remember to move the Mortgage class into its own Java file within the same package

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Lab3Theme {
        MortgageApp()
    }
}
