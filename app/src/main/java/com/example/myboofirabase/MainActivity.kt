package com.example.myboofirabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myboofirabase.data.Book
import com.example.myboofirabase.login.LoginScreen
import com.example.myboofirabase.login.data.LoginSreenObject
import com.example.myboofirabase.login.data.MainScreenDataObeject
import com.example.myboofirabase.main_screen.MainScreen
import com.example.myboofirabase.ui.theme.MyBooFiraBaseTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           val navController = rememberNavController()

            NavHost(navController = navController, startDestination = LoginSreenObject){
                composable<LoginSreenObject> {
                    LoginScreen{
                        navData ->navController.navigate(navData)
                    }
                }
                composable<MainScreenDataObeject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObeject>()
                    MainScreen(navData)
                }

            }
        }
    }
}
