package com.example.assesment1.ui.screen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assesment1.ui.navigation.Screen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable( route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }

        composable(route = Screen.BungaTabungan.route) {
            BungaTabungan(navController)
        }

        composable(route = Screen.BungaPinjaman.route) {
            BungaPinjaman(navController)
        }
    }
}