package com.astetech.omnifidelidade.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.astetech.omnifidelidade.R
import com.astetech.omnifidelidade.databinding.ActivityMainBinding
import java.util.Collections.list

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarApp)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.bottomNavMain.setupWithNavController(navController)

        val semMenu = listOf(R.id.loginFragment, R.id.profileDataFragment, R.id.chooseCredentialsFragment )

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.loginFragment, R.id.bonusFragment, R.id.notificacoesFragment, R.id.atividadesFragment)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isTopLevelDestination = appBarConfiguration.topLevelDestinations.contains(destination.id)
            if (!isTopLevelDestination){
                binding.toolbarApp.setNavigationIcon(R.drawable.ic_back)
            }

            val isSemMenu = semMenu.contains(destination.id)
            binding.bottomNavMain.visibility =  if(isSemMenu){
                View.GONE
            } else {
                View.VISIBLE
            }
    }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}