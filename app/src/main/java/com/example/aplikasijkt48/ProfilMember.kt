package com.example.aplikasijkt48

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme

@Composable
fun ProfilMember(nama: String, hobi: String, generasi: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(20.dp)) {
        Text(text = "Oshi akuh:")
        Text(text = nama)
        Text(text = "Hobi aku : $hobi")
        Text(text = "Generasi : $generasi")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        AplikasiJKT48Theme {
            ProfilMember(nama = "Freya Jayawardana", hobi = "menari", generasi = "7")
        }
}