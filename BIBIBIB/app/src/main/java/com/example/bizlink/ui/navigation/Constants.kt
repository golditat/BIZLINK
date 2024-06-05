package com.example.bizlink.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

object Constants {
    val BottomNavItems = listOf(
        NavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = "home"
        ),
        NavItem(
            label = "Calender",
            icon = Icons.Filled.DateRange,
            route = "calender"
        ),
        NavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
}
