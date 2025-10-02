package com.example.loginc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Garante que o Firebase seja inicializado antes de qualquer outra coisa.
        FirebaseApp.initializeApp(this)

        // Define o layout da atividade, que contém o nosso NavHostFragment.
        setContentView(R.layout.activity_main)

        // Encontra o NavHostFragment e obtém seu NavController.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configura a ActionBar para trabalhar com o NavController.
        // Isso mostrará os títulos dos fragmentos e o botão "voltar" automaticamente.
        setupActionBarWithNavController(navController)
    }

    // Permite que o botão "voltar" da ActionBar funcione corretamente com a navegação.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
