package com.example.bizlink.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.BLViewModel
import com.example.bizlink.ui.compose.AuthScreen
import com.example.bizlink.ui.compose.CalenderScreen
import com.example.bizlink.ui.compose.HomeScreen
import com.example.bizlink.ui.compose.ProfileScreen
import com.example.bizlink.ui.compose.ProjectScreen
import com.example.bizlink.ui.compose.RegScreen
import com.example.bizlink.ui.compose.TaskScreen

@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "auth",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("home/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) {backStackEntry->
                HomeScreen(navController, backStackEntry.arguments?.getInt("id"))
            }

            // route : calender
            composable("calender") {
                CalenderScreen(navController)
            }
            // route : profile
            composable("profile/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })){backStackEntry->
                ProfileScreen(navController, backStackEntry.arguments?.getInt("id")?: 0)
            }
            composable("task/{taskId}", arguments = listOf(navArgument("taskId") { type = NavType.IntType })){backStackEntry->
                TaskScreen(navController, backStackEntry.arguments?.getInt("taskId"))
            }
            composable("project/{projectId}",arguments = listOf(navArgument("projectId") { type = NavType.IntType })){backStackEntry->
                ProjectScreen(navController, backStackEntry.arguments?.getInt("projectId"))
            }
            composable("reg"){
                RegScreen(navController)
            }
            composable("auth"){
                AuthScreen(navController)
            }
        }
    )

}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 16.dp,
        modifier = Modifier
            .height(48.dp)
            .padding(vertical = 8.dp)) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        Constants.BottomNavItems.forEach { navItem ->

            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
    }
}