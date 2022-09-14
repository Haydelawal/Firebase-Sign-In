package com.example.firebaseuiauthenticationmarkusneuhoff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentEmailSignInBinding
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        binding.button.setOnClickListener {
            if (user != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToEmailSignInSuccessFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "No Account Found, Kindly Register", Toast.LENGTH_SHORT).show()
                val action =  HomeFragmentDirections.actionHomeFragmentToEmailSignInFragment()
                findNavController().navigate(action)
            }
        }

        binding.button2.setOnClickListener {
            if (user != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToGoogleSignInSuccessFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "No Account Found, Kindly Register", Toast.LENGTH_SHORT).show()
                val action =  HomeFragmentDirections.actionHomeFragmentToGoogleSignInFragment()
                findNavController().navigate(action)
            }
        }

        binding.button3.setOnClickListener {
            if (user != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToFbSignInSuccessFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "No Account Found, Kindly Register", Toast.LENGTH_SHORT).show()
                val action =  HomeFragmentDirections.actionHomeFragmentToFbLogInFragment()
                findNavController().navigate(action)
            }
        }


        binding.button5.setOnClickListener {
            if (user != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToPhoneLogInSuccessFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "No Account Found, Kindly Register", Toast.LENGTH_SHORT).show()
                val action =  HomeFragmentDirections.actionHomeFragmentToPhoneLogInFragment()
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