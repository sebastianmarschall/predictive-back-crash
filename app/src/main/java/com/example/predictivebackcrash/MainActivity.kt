package com.example.predictivebackcrash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.predictivebackcrash.ui.theme.PredictiveBackCrashTheme
import kotlinx.serialization.Serializable


object Routes {
    @Serializable
    data object ScreenA

    @Serializable
    data class RouteB(val color: Int) {
        @Serializable
        data object ScreenB
    }
}

class ScreenBViewModel : ViewModel()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PredictiveBackCrashTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.ScreenA,
                ) {
                    composable<Routes.ScreenA> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Yellow),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                navController.navigate(Routes.RouteB(Color.Red.toArgb()))
                            }) {
                                Text("To Red")
                            }
                        }
                    }
                    navigation<Routes.RouteB>(
                        startDestination = Routes.RouteB.ScreenB
                    ) {
                        composable<Routes.RouteB.ScreenB> { backStackEntry ->
                            val parentEntry = remember(backStackEntry) {
                                navController.getBackStackEntry<Routes.RouteB>()
                            }
                            val vm = viewModel<ScreenBViewModel>(parentEntry)
                            val route = parentEntry.toRoute<Routes.RouteB>()
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(route.color)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    Button(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Text("Back to Red")
                                    }
                                    Button(onClick = {
                                        navController.navigate(Routes.RouteB(Color.Green.toArgb()))
                                    }) {
                                        Text("To Green")
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PredictiveBackCrashTheme {
        Greeting("Android")
    }
}