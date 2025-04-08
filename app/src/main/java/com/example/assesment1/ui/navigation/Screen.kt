package com.example.assesment1.ui.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object About:Screen("aboutScreen")
    data object BungaTabungan:Screen("BungaTabungan")
    data object BungaPinjaman:Screen("BunganPinjaman")
}
