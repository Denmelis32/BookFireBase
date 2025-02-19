

package com.example.myboofirabase.add_book_screen

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myboofirabase.R
import com.example.myboofirabase.data.Book
import com.example.myboofirabase.datas.AddScreenObject
import com.example.myboofirabase.login.LoginButton
import com.example.myboofirabase.login.RoudedCornerTextField
import com.example.myboofirabase.login.signIn
import com.example.myboofirabase.login.signUp
import com.example.myboofirabase.ui.theme.BoxFilerColor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


@Composable
fun AddBookScreen(
    navData:AddScreenObject? = AddScreenObject(),
    onSaved: () -> Unit = {},
    onError: () -> Unit = {}
) {
    val cv = LocalContext.current.contentResolver
    var selectedCategory = "Bestsellers"
    val title = remember { mutableStateOf(navData?.name) }
    val description = remember { mutableStateOf(navData?.description) }
    val price = remember { mutableStateOf(navData?.price) }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val firestore = remember { Firebase.firestore }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri.value = uri
    }

    Image(
        painter = rememberAsyncImagePainter(model = selectedImageUri.value),
        contentDescription = "Background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray) // Замените на ваш цвет
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 40.dp, end = 40.dp),
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

        title.value?.let {
            RoudedCornerTextField(
                text = it,
                label = "Title"
            ) {
                title.value = it
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        price.value?.let {
            RoudedCornerTextField(
                text = it,
                label = "Price"
            ) {
                price.value = it
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        RoudedCornerDropMenu { selectedItem ->
            selectedCategory = selectedItem
        }
        Spacer(modifier = Modifier.height(10.dp))

        description.value?.let {
            RoudedCornerTextField(
                maxLines = 5,
                singLine = false,
            text = it,
            label = "Description"
            ) {
            description.value = it
        }
        }
        Spacer(modifier = Modifier.height(10.dp))

        LoginButton(text = "Select Image") {
            imageLauncher.launch("image/*")
        }
        LoginButton(text = "Save") {
            saveBookToFirestore(
                firestore,
                Book(
                    name = title.value.toString(),
                    description = description.value.toString(),
                    price = price.value.toString(),
                    category = selectedCategory,
                    imageUrl = imageToBase64(selectedImageUri.value!!, cv)
                ),
                onSaved = onSaved,
                onError = onError
            )
        }
    }
}

private fun imageToBase64(uri: Uri, contentResolver: ContentResolver): String {
    val inputStream = contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes()
    return bytes?.let {
        Base64.encodeToString(it, Base64.DEFAULT)
    } ?: ""
}

private fun saveBookToFirestore(
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
) {
    val db = firestore.collection("books")
    val key = db.document().id
    db.document(key).set(book.copy(key = key)).addOnSuccessListener {
        onSaved()
    }.addOnFailureListener {
        onError()
    }
}