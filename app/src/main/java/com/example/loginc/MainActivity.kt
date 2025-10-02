package com.example.loginc // <-- Verifique se seu pacote está correto

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // Declarações dos layouts e da barra de progresso
    private lateinit var loginLayout: ConstraintLayout
    private lateinit var profileLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        // Referências aos layouts principais e à barra de progresso
        loginLayout = findViewById(R.id.loginLayout)
        profileLayout = findViewById(R.id.profileLayout)
        progressBar = findViewById(R.id.progress_bar)

        // Verifica o estado do login quando a tela é criada
        updateUI()

        // --- Configuração dos botões e campos da tela de LOGIN ---
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha e-mail e senha.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                        updateUI() // Atualiza a tela para mostrar o perfil
                    } else {
                        Toast.makeText(this, "Falha no login: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // --- Configuração dos botões e campos da tela de PERFIL (LOGADO) ---
        val btnLogout = profileLayout.findViewById<Button>(R.id.btn_logout)

        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Você foi desconectado.", Toast.LENGTH_SHORT).show()
            updateUI() // Atualiza a tela para mostrar o login novamente
        }
    }

    private fun updateUI() {
        val user = auth.currentUser
        if (user != null) {
            // USUÁRIO ESTÁ LOGADO
            // Esconde a tela de login e mostra a tela de perfil
            loginLayout.visibility = View.GONE
            profileLayout.visibility = View.VISIBLE

            // Atualiza as informações na tela de perfil
            val tvUserEmail = profileLayout.findViewById<TextView>(R.id.tv_user_email)
            tvUserEmail.text = user.email
        } else {
            // USUÁRIO NÃO ESTÁ LOGADO
            // Mostra a tela de login e esconde a tela de perfil
            loginLayout.visibility = View.VISIBLE
            profileLayout.visibility = View.GONE
        }
    }
}
