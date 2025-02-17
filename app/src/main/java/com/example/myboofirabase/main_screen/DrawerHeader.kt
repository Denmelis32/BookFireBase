package com.example.myboofirabase.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myboofirabase.R
import com.example.myboofirabase.ui.theme.DarkBlue


@Composable
fun DrawerHeader() {
    Column(
        Modifier.fillMaxWidth()
            .height(170.dp)
            .background(DarkBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =  Arrangement.Center
    ) {
            Image(modifier = Modifier.size(90.dp),
                painter = painterResource(R.drawable.books), contentDescription = "" )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Melekhov Book",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
    }
}