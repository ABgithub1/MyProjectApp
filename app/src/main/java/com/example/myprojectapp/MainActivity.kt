package com.example.myprojectapp

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myprojectapp.databinding.ActivityMainBinding
import com.example.myprojectapp.extentions.applySelectedAppLanguage
import com.example.myprojectapp.model.settings.NightMode
import com.example.myprojectapp.service.LanguageService
import com.example.myprojectapp.service.NightModeService
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val languageService by inject<LanguageService>()
    private val nightModeService by inject<NightModeService>()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.applySelectedAppLanguage(languageService.language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigationBar

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_weather
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initNightMode()
    }

    private fun initNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            when (nightModeService.nightMode) {
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }

}