package com.example.androidx.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.androidx.R

class MainActivity : AppCompatActivity() {

    private lateinit var _navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,_navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(_navController,null)
    }
}