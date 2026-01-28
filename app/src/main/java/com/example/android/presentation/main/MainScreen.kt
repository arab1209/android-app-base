package com.example.android.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.presentation.home.HomeScreen
import com.example.android.presentation.other.OtherScreen
import com.example.android.presentation.otherthree.OtherThreeScreen
import com.example.android.presentation.othertwo.OtherTwoScreen

@Composable
fun MainScreen () {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController)
        }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("other") {
                OtherScreen()
            }
            composable("other_two") {
                OtherTwoScreen()
            }
            composable("other_three") {
                OtherThreeScreen()
            }
        }
    }
}