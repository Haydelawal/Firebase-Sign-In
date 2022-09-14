package com.example.firebaseuiauthenticationmarkusneuhoff.signin.phone_sign_in

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentPhoneLogInBinding
import com.example.firebaseuiauthenticationmarkusneuhoff.signin.phone_sign_in.data.MyCodeSentData
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class PhoneLogInFragment : Fragment() {

    private lateinit var _binding: FragmentPhoneLogInBinding
    private val binding get() = _binding!!

    companion object{
        const val TAG = "phonelogin"
    }

    private lateinit var sentVerificationId: String
//    private lateinit var sentPhoneNo: String
    private lateinit var sendResendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var sendCodeSentData: MyCodeSentData

    private lateinit var sentPhoneNumber: String

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneLogInBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()

        binding.phoneProgressBar.visibility = View.INVISIBLE


        binding.sendOTPBtn.setOnClickListener {
            var phoneNumber = binding.phoneEditTextNumber.text.toString()

            if (phoneNumber.isNotEmpty()) {

                if (phoneNumber.length == 10) {
                    phoneNumber = "+234$phoneNumber"

                    sentPhoneNumber = phoneNumber

                    binding.phoneProgressBar.visibility = View.VISIBLE


                    val options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                } else {
                    Toast.makeText(
                        requireContext(),
                        "PLEASE ENTER A PHONE CORRECT NUMBER",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "PLEASE ENTER A PHONE NUMBER",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



        return binding.root
    }

  private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d(TAG, "onVerificationCompleted:$e")

            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d(TAG, "onVerificationCompleted:$e")

            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:$verificationId")
            Log.d(TAG, "onCodeSent:$token")

            sentVerificationId = verificationId
            sendResendToken = token

           val myCodeDataSent = MyCodeSentData(verificationId, token, phoneNo = sentPhoneNumber)

            sendCodeSentData = myCodeDataSent

            val action = PhoneLogInFragmentDirections.actionPhoneLogInFragmentToPhoneEnterOTPFragment(myCodeDataSent)
            findNavController().navigate(action)

            binding.phoneProgressBar.visibility = View.INVISIBLE

            // Save verification ID and resending token so we can use them later
//            storedVerificationId = verificationId
//            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
//                    val action = PhoneLogInFragmentDirections.
                    Toast.makeText(requireContext(), "Auth Successful", Toast.LENGTH_SHORT).show()

                    val user = task.result?.user

                    val action = PhoneLogInFragmentDirections.actionPhoneLogInFragmentToPhoneEnterOTPFragment(sendCodeSentData)
                    findNavController().navigate(action)

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding.root
    }
}