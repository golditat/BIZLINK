package com.example.bizlink.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.navigation.compose.rememberNavController
import com.example.bizlink.di.ServiceLocator
import com.example.bizlink.ui.navigation.BottomNavigationBar
import com.example.bizlink.ui.navigation.NavHostContainer


class MainActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            ServiceLocator.initDataDependencies(applicationContext)
            ServiceLocator.initDomainDependencies()
            ViewModelProvider(this).get(BLViewModel::class.java)
            Surface(color = Color.White) {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }, content = { padding ->
                        NavHostContainer(navController = navController, padding = padding)
                    }
                )
            }
        }
    }

}