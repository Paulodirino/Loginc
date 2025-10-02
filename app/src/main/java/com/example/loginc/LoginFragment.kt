package com.example.loginc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.loginc.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inicializa o Firebase Auth aqui. É seguro e um pouco mais cedo.
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Verifica o usuário atual aqui. É mais seguro para a navegação inicial.
        if (auth.currentUser != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
            findNavController().navigate(action)
            return // Retorna para não configurar os listeners abaixo desnecessariamente.
        }

        binding.registerButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                binding.messageTextView.text = "E-mail e senha são obrigatórios."
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("AUTH", "createUserWithEmail:success")
                        binding.messageTextView.text = "Registro bem-sucedido!"
                    } else {
                        Log.w("AUTH", "createUserWithEmail:failure", task.exception)
                        binding.messageTextView.text = "Falha no registro: ${task.exception?.message}"
                    }
                }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                binding.messageTextView.text = "E-mail e senha são obrigatórios."
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d("AUTH", "signInWithEmail:success")
                        val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
                        findNavController().navigate(action)
                    } else {
                        Log.w("AUTH", "signInWithEmail:failure", task.exception)
                        binding.messageTextView.text = "Falha no login: ${task.exception?.message}"
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
