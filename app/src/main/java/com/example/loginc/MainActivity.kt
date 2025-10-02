package com.example.loginc

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.loginc.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o Firebase
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        // Configura o View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura os listeners dos botões
        setupButtonClickListeners()

        // Atualiza a UI com base no estado de login
        updateUI()
    }

    private fun setupButtonClickListeners() {
        // Botão de Registro
        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                binding.messageTextView.text = "E-mail e senha são obrigatórios."
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("AUTH", "createUserWithEmail:success")
                        binding.messageTextView.text = "Registro bem-sucedido! Faça o login."
                    } else {
                        Log.w("AUTH", "createUserWithEmail:failure", task.exception)
                        binding.messageTextView.text = "Falha no registro: ${task.exception?.message}"
                    }
                }
        }

        // Botão de Login
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                binding.messageTextView.text = "E-mail e senha são obrigatórios."
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("AUTH", "signInWithEmail:success")
                        updateUI() // Atualiza a UI para mostrar a tela de perfil
                    } else {
                        Log.w("AUTH", "signInWithEmail:failure", task.exception)
                        binding.messageTextView.text = "Falha no login: ${task.exception?.message}"
                    }
                }
        }

        // Botão de Logout
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            updateUI() // Atualiza a UI para mostrar a tela de login
        }
    }

    private fun updateUI() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Usuário está logado: mostra a tela de perfil
            binding.loginLayout.visibility = View.GONE
            binding.profileLayout.visibility = View.VISIBLE
            binding.welcomeTextView.text = "Bem-vindo, ${currentUser.email}!"
        } else {
            // Usuário não está logado: mostra a tela de login
            binding.loginLayout.visibility = View.VISIBLE
            binding.profileLayout.visibility = View.GONE
            binding.messageTextView.text = "" // Limpa a mensagem de erro/sucesso
            binding.emailEditText.text.clear()
            binding.passwordEditText.text.clear()
        }
    }
}
