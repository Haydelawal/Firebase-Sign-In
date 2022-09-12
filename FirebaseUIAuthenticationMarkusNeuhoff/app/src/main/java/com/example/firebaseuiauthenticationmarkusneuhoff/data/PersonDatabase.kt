package com.example.firebaseuiauthenticationmarkusneuhoff.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.data.PersonDao

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = false
)
abstract class PersonDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao

}