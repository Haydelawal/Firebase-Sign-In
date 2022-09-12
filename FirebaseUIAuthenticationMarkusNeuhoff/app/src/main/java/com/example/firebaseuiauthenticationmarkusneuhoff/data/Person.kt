package com.example.firebaseuiauthenticationmarkusneuhoff.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Parcelize
@Entity(tableName = "person_table")
data class Person(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,

    val dateCreated: Long = System.currentTimeMillis(),
    val gender: String,
    val age: Int

) : Parcelable {


    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(dateCreated)
}
