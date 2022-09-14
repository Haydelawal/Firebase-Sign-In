package com.example.firebaseuiauthenticationmarkusneuhoff.signin.twitter_sign_in

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentTwitterLogInBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider


class TwitterLogInFragment : Fragment() {

    private lateinit var _binding: FragmentTwitterLogInBinding
    private val binding get() = _binding!!
    private lateinit var provider: OAuthProvider.Builder
//    private lateinit var _pendingResultTask: Task<AuthResult>
//    private val pendingResultTask get() = _pendingResultTask!!

    private lateinit var mAuth: FirebaseAuth

    companion object{
        const val myTag = "myTag"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTwitterLogInBinding.inflate(inflater, container, false)

        mAuth = FirebaseAuth.getInstance()

        provider = OAuthProvider.newBuilder("twitter.com");


        binding.button3.setOnClickListener {
            TwitterProvider()
        }

        return binding.root
    }

    private fun TwitterProvider() {

        val pendingResultTask: Task<AuthResult>? = mAuth.pendingAuthResult

//         _pendingResultTask = mAuth.getPendingAuthResult()

        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // authResult.getCredential().getAccessToken().
                    // The OAuth secret can be retrieved by calling:
                    // authResult.getCredential().getSecret().
                    Toast.makeText(requireContext(), "1 S", Toast.LENGTH_SHORT).show()
                    Log.d(myTag, " 1 S = ${it.toString()}")
                }
                .addOnFailureListener {
                    // Handle failure.
                    Toast.makeText(requireContext(), "1 E", Toast.LENGTH_SHORT).show()
                    Log.d(myTag, " 1 E = ${it.toString()}")

                }
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            mAuth
                .startActivityForSignInWithProvider( /* activity= */requireActivity(), provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // IdP data available in
                        // authResult.getAdditionalUserInfo().getProfile().
                        // The OAuth access token can also be retrieved:
                        // authResult.getCredential().getAccessToken().
                        // The OAuth secret can be retrieved by calling:
                        // authResult.getCredential().getSecret().
                        Log.d(myTag, " 2 S = ${it.toString()}")

                        Toast.makeText(requireContext(), "2 S", Toast.LENGTH_SHORT).show()

                        val action = TwitterLogInFragmentDirections.actionTwitterLogInFragmentToTwitterLogInSuccessFragment()
                        findNavController().navigate(action)

                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                        Toast.makeText(requireContext(), "2 E", Toast.LENGTH_SHORT).show()
                        Log.d(myTag, "2 E = ${it.toString()}")

                    })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }

}