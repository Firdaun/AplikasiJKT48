package com.example.aplikasijkt48

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Application JKT48", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            ProfilMember(
                nama = "Freya Jayawardana",
                hobi = "menari",
                generasi = "7"
            )

            ProfilMember(
                nama = "Jacqueline Immanuela",
                hobi = "nyanyi",
                generasi = "13"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DesainLayarUtamaPreview() {
    AplikasiJKT48Theme {
        DesainLayarUtama()
    }
}