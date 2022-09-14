package com.example.firebaseuiauthenticationmarkusneuhoff.signin.phone_sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentPhoneLogInSuccessBinding
import com.example.firebaseuiauthenticationmarkusneuhoff.signin.google_sign_in.GoogleSignInSuccessFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class PhoneLogInSuccessFragment : Fragment() {

    private lateinit var _binding: FragmentPhoneLogInSuccessBinding
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneLogInSuccessBinding.inflate(inflater, container, false)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        binding.apply {
            username.text = user?.email
            binding.password.text = user?.uid.toString()

            backButton.setOnClickListener {
                val action = PhoneLogInSuccessFragmentDirections.actionPhoneLogInSuccessFragmentToHomeFragment()
                findNavController().navigate(action)
            }

            signoutButton.setOnClickListener {

                Firebase.auth.signOut()

                val action = PhoneLogInSuccessFragmentDirections.actionPhoneLogInSuccessFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}