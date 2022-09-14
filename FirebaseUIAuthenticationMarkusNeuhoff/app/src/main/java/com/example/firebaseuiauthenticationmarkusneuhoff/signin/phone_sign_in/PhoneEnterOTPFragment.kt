package com.example.firebaseuiauthenticationmarkusneuhoff.signin.phone_sign_in

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebaseuiauthenticationmarkusneuhoff.R
import com.example.firebaseuiauthenticationmarkusneuhoff.databinding.FragmentPhoneEnterOTPBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class PhoneEnterOTPFragment : Fragment() {

    private lateinit var _binding: FragmentPhoneEnterOTPBinding
    private val binding get() = _binding!!

    private val args: PhoneEnterOTPFragmentArgs by navArgs()
    private lateinit var mAuth: FirebaseAuth

    private lateinit var myVerificationId: String
    private lateinit var phoneNo: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneEnterOTPBinding.inflate(inflater, container, false)
        mAuth = FirebaseAuth.getInstance()

        phoneNo = args.myCodeDataSent.phoneNo
        myVerificationId = args.myCodeDataSent.verificationId
        resendToken = args.myCodeDataSent.token

        binding.otpProgressBar.visibility = View.INVISIBLE
        addTextChangeListener()
        resendOTPTextViewVisibility()

        binding.verifyOTPBtn.setOnClickListener {
            val typedOTP = (binding.otpEditText1.text.toString() + binding.otpEditText2.text.toString() + binding.otpEditText3.text.toString() + binding.otpEditText4.text.toString()
                    + binding.otpEditText5.text.toString() + binding.otpEditText6.text.toString())

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                         myVerificationId , typedOTP)
                    Log.w(PhoneLogInFragment.TAG, "typedOTP verifID ${args.myCodeDataSent.verificationId}")
                    Log.w(PhoneLogInFragment.TAG, "typedOTP Token ${args.myCodeDataSent.token}")


                    binding.otpProgressBar.visibility = View.VISIBLE
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(requireContext(), "PLEASE ENTER CORRECT OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "NO OTP INPUTTED", Toast.LENGTH_SHORT).show()
            }

        }

        binding.resendTextView.setOnClickListener {
            resendVerificationOTP()
            resendOTPTextViewVisibility()
        }

        return binding.root
    }

    private fun resendOTPTextViewVisibility() {
        binding.otpEditText1.setText("")
        binding.otpEditText2.setText("")
        binding.otpEditText3.setText("")
        binding.otpEditText4.setText("")
        binding.otpEditText5.setText("")
        binding.otpEditText6.setText("")

        binding.resendTextView.apply {
            visibility = View.INVISIBLE
            isEnabled = false
        }

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.resendTextView.apply {
                visibility = View.VISIBLE
                isEnabled = true
            }
        }, 60000)
    }

    private fun resendVerificationOTP() {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNo)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun addTextChangeListener() {
        binding.otpEditText1.addTextChangedListener(EditTextWatcher(binding.otpEditText1))
        binding.otpEditText2.addTextChangedListener(EditTextWatcher(binding.otpEditText2))
        binding.otpEditText3.addTextChangedListener(EditTextWatcher(binding.otpEditText3))
        binding.otpEditText4.addTextChangedListener(EditTextWatcher(binding.otpEditText4))
        binding.otpEditText5.addTextChangedListener(EditTextWatcher(binding.otpEditText5))
        binding.otpEditText6.addTextChangedListener(EditTextWatcher(binding.otpEditText6))

    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(PhoneLogInFragment.TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(PhoneLogInFragment.TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
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
            Log.d(PhoneLogInFragment.TAG, "onCodeSent:$verificationId")


//
//            val action = PhoneEnterOTPFragmentDirections.actionPhoneEnterOTPFragmentToPhoneLogInSuccessFragment()
//            findNavController().navigate(action)
                myVerificationId = verificationId
                resendToken = token
            binding.resendTextView.visibility = View.VISIBLE

//             Save verification ID and resending token so we can use them later
//            storedVerificationId = verificationId
//            resendToken = token
        }
    }


    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            Toast.makeText(requireContext(), "etwbtc", Toast.LENGTH_SHORT).show()
            }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            Toast.makeText(requireContext(), "etwbotc", Toast.LENGTH_SHORT).show()
        }

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()

            when (view.id) {
    R.id.otpEditText1 -> if (text.length == 1) binding.otpEditText2.requestFocus()
    R.id.otpEditText2 -> if (text.length == 1) binding.otpEditText3.requestFocus() else if (text.isEmpty()) binding.otpEditText1.requestFocus()
    R.id.otpEditText3 -> if (text.length == 1) binding.otpEditText4.requestFocus() else if (text.isEmpty()) binding.otpEditText2.requestFocus()
    R.id.otpEditText4 -> if (text.length == 1) binding.otpEditText5.requestFocus() else if (text.isEmpty()) binding.otpEditText3.requestFocus()
    R.id.otpEditText5 -> if (text.length == 1) binding.otpEditText6.requestFocus() else if (text.isEmpty()) binding.otpEditText4.requestFocus()
    R.id.otpEditText6 -> if (text.isEmpty()) binding.otpEditText5.requestFocus()

            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(PhoneLogInFragment.TAG, "signInWithCredential:success")
//                    val action = PhoneLogInFragmentDirections.
                    Toast.makeText(requireContext(), "Auth2 Successful", Toast.LENGTH_SHORT).show()

                    val user = task.result?.user
                    binding.otpProgressBar.visibility = View.INVISIBLE


                    val action = PhoneEnterOTPFragmentDirections.actionPhoneEnterOTPFragmentToPhoneLogInSuccessFragment()
                    findNavController().navigate(action)

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(PhoneLogInFragment.TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(requireContext(), "Auth2 failed", Toast.LENGTH_SHORT).show()

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
