package com.example.assesment1.ui.screen

import androidx.lifecycle.ViewModel
import com.example.assesment1.model.MenuItem
import com.example.assesment1.ui.navigation.Screen

class dataItems : ViewModel() {
    val data = listOf(
        MenuItem(
            "Menghitung Bunga Tabungan(Flat)",
            Screen.BungaTabungan.route,
            "Fitur untuk menghitung bunga dari tabungan Anda dengan rumus : \nBunga = Pokok x Suku Bunga x Waktu"
        ),
        MenuItem(
            "Menghitung Bunga Pinjam + Angsuran(Flat)",
            Screen.BungaPinjaman.route,
            "Fitur untuk menghitung bunga dari pinjaman Anda dengan rumus : \nBunga = Pokok x Suku Bunga x Lama Pinjaman\nAngsuran = (Pokok + Total Bunga) / Lama Pinjaman"
        )
    )
}
