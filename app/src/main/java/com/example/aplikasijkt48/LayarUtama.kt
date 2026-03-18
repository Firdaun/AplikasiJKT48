package com.example.aplikasijkt48

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import android.graphics.BlurMaskFilter
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.draw.scale
import com.example.aplikasijkt48.ui.theme.AplikasiJKT48Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesainLayarUtama() {
    val context = LocalContext.current
    var fotoSaatIni by remember { mutableIntStateOf(R.drawable.fritzy_dimalamhari) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.height(100.dp),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        LogoJKT48()
                        Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "JKT48",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                val warnaMedia = Color(0xFFF0244C)
                                Text(
                                    text = "MEDIA",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = warnaMedia,
                                    modifier = Modifier
                                        .background(
                                            color = warnaMedia.copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = warnaMedia,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp),

                                    style = TextStyle(
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    )
                                )
                            }
                            Text(
                                text = "Official Fan Gallery",
                                color = Color.White,
                                fontSize = 13.sp,
                                style = TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF07070F)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                ProfilMember(
                    nama = "Freya",
                    hobi = "menari",
                    generasi = "7"
                )

                ProfilMember(
                    nama = "Ekin",
                    hobi = "nyanyi",
                    generasi = "13"
                )
            }
            Image(
                painter = painterResource(id = fotoSaatIni),
                contentDescription = "Foto Bersama",
                modifier = Modifier
                    .padding(start = 50.dp, end = 50.dp, bottom = 50.dp)
                    .clip(RoundedCornerShape(20.dp))

            )
            Button(
                onClick = {
                    fotoSaatIni = if (fotoSaatIni == R.drawable.fritzy_dimalamhari) {
                        R.drawable.ic_launcher_background
                    } else {
                        R.drawable.fritzy_dimalamhari
                    }
                    Toast.makeText(
                        context,
                        "Tombol telah di ubah",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2883E5),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = "Interact",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun LogoJKT48(modifier: Modifier = Modifier) {
    val warnaGradasi = Brush.linearGradient(
        colors = listOf(
            Color(0xFFEE1D52),
            Color(0xFFC01240)
        )
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(40.dp)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val paint = Paint()
                    val frameworkPaint = paint.asFrameworkPaint()

                    frameworkPaint.color = Color(0xFFEE1D52).copy(alpha = 0.70f).toArgb()
                    frameworkPaint.maskFilter = BlurMaskFilter(30f, BlurMaskFilter.Blur.NORMAL)
                    canvas.drawRoundRect(
                        left = 0f,
                        top = 4f,
                        right = size.width,
                        bottom = size.height + 4f,
                        radiusX = 10.dp.toPx(),
                        radiusY = 10.dp.toPx(),
                        paint = paint
                    )
                }
            }
            .background(brush = warnaGradasi, shape = RoundedCornerShape(10.dp))
    ) {
        Canvas(
            modifier = Modifier
                .size(24.dp)
                .scale(0.9f)
        ) {
            val skalaLayar = this.density
            scale(scaleX = skalaLayar, scaleY = skalaLayar, pivot = Offset.Zero) {
                val gayaGaris = Stroke(
                    width = 2f,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )

                val pathBintang = PathParser().parsePathString(
                    "M11.017 2.814a1 1 0 0 1 1.966 0l1.051 5.558a2 2 0 0 0 1.594 1.594l5.558 1.051a1 1 0 0 1 0 1.966l-5.558 1.051a2 2 0 0 0-1.594 1.594l-1.051 5.558a1 1 0 0 1-1.966 0l-1.051-5.558a2 2 0 0 0-1.594-1.594l-5.558-1.051a1 1 0 0 1 0-1.966l5.558-1.051a2 2 0 0 0 1.594-1.594z"
                ).toPath()
                drawPath(path = pathBintang, color = Color.White, style = gayaGaris)

                val pathVertical = PathParser().parsePathString("M20 2v4").toPath()
                drawPath(path = pathVertical, color = Color.White, style = gayaGaris)

                val pathHorizontal = PathParser().parsePathString("M22 4h-4").toPath()
                drawPath(path = pathHorizontal, color = Color.White, style = gayaGaris)

                drawCircle(
                    color = Color.White,
                    radius = 2f,
                    center = Offset(4f, 20f),
                    style = gayaGaris
                )
            }
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