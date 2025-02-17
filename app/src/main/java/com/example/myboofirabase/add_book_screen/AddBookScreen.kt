package com.example.myboofirabase.add_book_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myboofirabase.R
import com.example.myboofirabase.login.LoginButton
import com.example.myboofirabase.login.RoudedCornerTextField
import com.example.myboofirabase.login.signIn
import com.example.myboofirabase.login.signUp
import com.example.myboofirabase.ui.theme.BoxFilerColor

@Preview(showBackground = true)
@Composable
fun AddBookScreen() {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val price = remember {
        mutableStateOf("")
    }
    val selectedImageUr = remember {
        mutableStateOf<Uri?>(null)
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        uri -> selectedImageUr.value = uri
    }

    Image(
        painter = rememberAsyncImagePainter(model = selectedImageUr.value), contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilerColor)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 40.dp,
                end = 40.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.free), contentDescription = "Icons")
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Add New Book",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,

            )
        Spacer(modifier = Modifier.height(10.dp))
        RoudedCornerTextField(
            text = title.value,
            label = "Title"
        ) {
            title.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoudedCornerTextField(
            text = price.value,
            label = "Price"
        ) {
            price.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoudedCornerTextField(
            maxLines = 5,
            singLine = false,
            text = description.value,
            label = "Description"
        ) {
            description.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(text = "Select Image") {
                imageLauncher.launch("image/*")
        }
        LoginButton(text = "Save") {

        }


    }
}
