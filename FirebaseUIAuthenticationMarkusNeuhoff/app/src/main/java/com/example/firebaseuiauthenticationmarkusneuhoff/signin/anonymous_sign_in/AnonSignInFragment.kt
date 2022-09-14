package com.example.firebaseuiauthenticationmarkusneuhoff.signin.anonymous_sign_in

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentAnonSignInBinding
import com.google.firebase.auth.FirebaseAuth


class AnonSignInFragment : Fragment() {

    private lateinit var _binding: FragmentAnonSignInBinding
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    companion object{
        const val TAG = "myTag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnonSignInBinding.inflate(inflater, container, false)

        mAuth = FirebaseAuth.getInstance()

        binding.button3.setOnClickListener {
            anonAuth()
        }

        return binding.root
    }

    private fun anonAuth() {
        mAuth.signInAnonymously()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success ${task.toString()}")

                    val user = mAuth.currentUser

                    Toast.makeText(requireContext(), "Authentication successful.",
                        Toast.LENGTH_SHORT).show()

                    val action = AnonSignInFragmentDirections.actionAnonSignInFragmentToAnonSignInSucessFragment()
                    findNavController().navigate(action)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}