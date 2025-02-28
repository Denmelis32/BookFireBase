package com.example.myboofirabase.main_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.myboofirabase.data.Book
import com.example.myboofirabase.login.data.MainScreenDataObeject
import com.example.myboofirabase.main_screen.button_menu.BottomMenu
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    navData: MainScreenDataObeject,
    onBookEditClick: (Book) -> Unit,
    onAdminClick: () -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val booksListState = remember { mutableStateOf(emptyList<Book>()) }
    val isAdminState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        getAllBooks(db) { books ->
            booksListState.value = books
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(navData.email)
                DrawerBody(onAdmin = { isAdmin ->
                    isAdminState.value = isAdmin
                }) {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onAdminClick()
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu() }
        ) { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(booksListState.value) { book ->
                    BookListItemUi(book = book, showEditButton = isAdminState.value) { clickedBook ->
                        onBookEditClick(clickedBook)
                    }
                }
            }
        }
    }
}

private fun getAllBooks(db: FirebaseFirestore, onBook: (List<Book>) -> Unit) {
    db.collection("books").get()
        .addOnSuccessListener { task ->
            onBook(task.toObjects(Book::class.java))
        }
        .addOnFailureListener { exception ->
            Log.e("FirestoreError", "Error getting documents: ", exception)
        }
}
