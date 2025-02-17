package com.example.myboofirabase.main_screen.button_menu

import com.example.myboofirabase.R

sealed class BottomMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Home :BottomMenuItem(
        route = "",
        title = "Home",
        iconId = R.drawable.ic_home
    )
    object Favs :BottomMenuItem(
        route = "",
        title = "Favs",
        iconId = R.drawable.ic_favs
    )
    object Settings :BottomMenuItem(
        route = "",
        title = "Settings",
        iconId = R.drawable.ic_settings
    )
}