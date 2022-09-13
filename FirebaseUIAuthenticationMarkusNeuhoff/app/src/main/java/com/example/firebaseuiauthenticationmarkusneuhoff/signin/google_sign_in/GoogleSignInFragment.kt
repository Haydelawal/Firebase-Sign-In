package com.example.firebaseuiauthenticationmarkusneuhoff.signin.google_sign_in

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.R
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentGoogleSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class GoogleSignInFragment : Fragment() {
    private lateinit var _binding: FragmentGoogleSignInBinding
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object{
        const val RC_SIGN_IN = 120
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGoogleSignInBinding.inflate(inflater, container, false)


        //
       val signInRequest = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken(getString(R.string.default_web_client_id))
           .requestEmail()
           .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), signInRequest)

        mAuth = FirebaseAuth.getInstance()

        binding.button3.setOnClickListener {
            signIn()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("GSI Fragment", "firebase auth with google" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.d("GSI Fragment", "firebase auth with google failed", e)

                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("GSI Fragment", "task not successful ${exception.toString()}")

            }

        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("GSI", "success ${mAuth.uid}")
                    val user = mAuth.currentUser

                    val action =GoogleSignInFragmentDirections.actionGoogleSignInFragmentToGoogleSignInSuccessFragment()
                    findNavController().navigate(action)
                } else {
                    // if sign in fails
                    Log.d("GSI", "failed", task.exception)

                }
            }
    }
}