package com.example.firebaseuiauthenticationmarkusneuhoff.signin.phone_sign_in.data

import android.os.Parcelable
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyCodeSentData(
    val verificationId: String,
    val token: PhoneAuthProvider.ForceResendingToken,
    val phoneNo: String
) : Parcelable
