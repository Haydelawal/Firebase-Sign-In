package com.example.firebaseuiauthenticationmarkusneuhoff.signin.email_signin

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentEmailSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi


class Email_SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth

    private lateinit var _binding: FragmentEmailSignInBinding
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmailSignInBinding.inflate(inflater, container, false)

        auth = Firebase.auth


        binding.registerButton.setOnClickListener {


            val emailData = binding.loginEmail.text.toString()
            val passwordData = binding.loginPassword.text.toString()

            if (emailData.isEmpty() or emailData.isBlank()) {
                Toast.makeText(requireContext(), "Please input email field", Toast.LENGTH_SHORT).show()
            }

            else if (passwordData.isEmpty() or passwordData.isBlank()) {
                Toast.makeText(requireContext(), "Please input password field", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Check if user is signed in (non-null) and update UI accordingly.
                val currentUser = auth.currentUser

                    auth.createUserWithEmailAndPassword(emailData, passwordData)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                Toast.makeText(requireContext(), "Successfully Created New User", Toast.LENGTH_SHORT).show()

                                // redirect

                                val action = Email_SignInFragmentDirections.actionEmailSignInFragmentToEmailSignInSuccessFragment()
                                findNavController().navigate(action)

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(requireContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(), "Didnt create New User", Toast.LENGTH_SHORT).show()

                            }
                        }
                }
            }




        binding.backButton.setOnClickListener {
            val action = Email_SignInFragmentDirections.actionEmailSignInFragmentToHomeFragment()
            findNavController().navigate(action)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}