package com.example.assesment1.ui.navigation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assesment1.R
import com.example.assesment1.ui.theme.Assesment1Theme
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BungaTabungan(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.bunga_tabungan))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    var pokok by rememberSaveable { mutableStateOf("") }
    var bunga by rememberSaveable { mutableStateOf("") }
    var lamaWaktu by rememberSaveable { mutableStateOf("") }
    var hasil by rememberSaveable { mutableDoubleStateOf(0.0) }
    var totalBunga by rememberSaveable { mutableDoubleStateOf(0.0) }
    var jenisWaktu by rememberSaveable { mutableStateOf("Bulanan") }

    val opsiWaktu = listOf("Bulanan", "Tahunan")
    val currencyFormatter = NumberFormat.getNumberInstance(Locale("id", "ID")).apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    val totalBungaFormatted = currencyFormatter.format(totalBunga)
    val totalTabunganFormatted = currencyFormatter.format(hasil )

    val context = LocalContext.current

    LaunchedEffect(jenisWaktu) {
        lamaWaktu = ""
        hasil = 0.0
        totalBunga = 0.0
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kalkulator Bunga Tabungan",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pokok,
            onValueChange = { pokok = it },
            label = { Text("Jumlah Tabungan (Rp)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bunga,
            onValueChange = { bunga = it },
            label = { Text(stringResource(R.string.bunga)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lamaWaktu,
            onValueChange = {
                lamaWaktu = if (jenisWaktu == "Bulanan") {
                    it.filter { char -> char.isDigit() }
                } else {
                    it
                }
            },
            label = {
                Text(
                    if (jenisWaktu == "Bulanan") "Lama Menabung (bulan)" else "Lama Menabung (tahun)"
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            opsiWaktu.forEach { opsi ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = jenisWaktu == opsi,
                            onClick = { jenisWaktu = opsi },
                            role = Role.RadioButton
                        )
                        .padding(8.dp)
                ) {
                    RadioButton(
                        selected = jenisWaktu == opsi,
                        onClick = { jenisWaktu = opsi }
                    )
                    Text(text = opsi)
                }
            }
        }

        Button(
            onClick = {
                if (pokok.isNotEmpty() && bunga.isNotEmpty() && lamaWaktu.isNotEmpty()) {
                    val p = pokok.replace(".", "").replace(",", "").toDouble()
                    val r = bunga.replace(",", ".").toDouble() / 100
                    val t = if (lamaWaktu == "Bulanan") lamaWaktu.toDouble() else lamaWaktu.replace(",", ".").toDouble()

                    val lamaDalamTahun = if (jenisWaktu == "Bulanan") t / 12.0 else t
                    totalBunga = p * r * lamaDalamTahun
                    hasil = p + totalBunga
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.hitung))
        }

        if (hasil != 0.0) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = context.getString(R.string.total_bunga, totalBungaFormatted),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = context.getString(R.string.total_tabungan, totalTabunganFormatted),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BungaTabunganPreview() {
    Assesment1Theme {
        BungaTabungan(rememberNavController())
    }
}