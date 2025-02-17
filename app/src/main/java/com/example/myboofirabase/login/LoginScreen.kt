package com.example.myboofirabase.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myboofirabase.R
import com.example.myboofirabase.login.data.MainScreenDataObeject
import com.example.myboofirabase.ui.theme.BoxFilerColor
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(
    onNavigateToMainScreen:(MainScreenDataObeject) -> Unit
) {


    val auth = remember {
        Firebase.auth
    }

    val errorState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("denmelis@gmail.ru")
    }
    val passwordState = remember {
        mutableStateOf("12345678")
    }
    Image(
        painter = painterResource(id = R.drawable.books), contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
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
            text = "Melekhov Book",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif,

            )
        Spacer(modifier = Modifier.height(100.dp))
        RoudedCornerTextField(
            text = emailState.value,
            label = "Email"
        ) {
            emailState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoudedCornerTextField(
            text = passwordState.value,
            label = "Password"
        ) {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                textAlign = TextAlign.Center

                )
        }
        LoginButton(text = "Sign In") {
            signIn(
                auth,
                emailState.value,
                passwordState.value,
                onSignInSuccess = { navData ->
                    onNavigateToMainScreen(navData)
                },
                onSignInFailure = { error -> errorState.value = error }
            )
        }
        LoginButton(text = "Sign Up") {
            signUp(
                auth,
                emailState.value,
                passwordState.value,
                onSignUpSuccess = {navData -> onNavigateToMainScreen(navData) },
                onSignUpFailure = { error -> errorState.value = error }
            )
        }


    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: (MainScreenDataObeject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignUpFailure("Email and password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onSignUpSuccess(
                    MainScreenDataObeject (
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
            )
        }
    }.addOnFailureListener {
        onSignUpFailure(it.message ?: "Sign Up Error")
    }
}
fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (MainScreenDataObeject) -> Unit,
    onSignInFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignInFailure("Email and password cannot be empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onSignInSuccess(
                MainScreenDataObeject (
                    task.result.user?.uid!!,
                    task.result.user?.email!!
                )
            )
        }
    }.addOnFailureListener {
        onSignInFailure(it.message ?: "Sign In  Error")
    }
}