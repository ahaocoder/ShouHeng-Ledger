package com.example.ledger

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ledger.data.database.LedgerDatabase
import com.example.ledger.screens.HomePage
import com.example.ledger.screens.ProfilePage
import com.example.ledger.screens.SettingPage

@Composable
fun Routes(db: LedgerDatabase) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomePage(navController, db) }
        composable("setting") { SettingPage(navController) }
        composable("profile") { ProfilePage(navController) }
    }
}