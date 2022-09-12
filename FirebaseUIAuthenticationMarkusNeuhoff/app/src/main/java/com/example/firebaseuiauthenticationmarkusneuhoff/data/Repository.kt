package com.example.firebaseuiauthenticationmarkusneuhoff.data

import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.data.PersonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

 class Repository @Inject constructor(
    private val personDao: PersonDao
) {

    fun readData(): Flow<List<Person>> {
        return personDao.readData()
    }

    suspend fun insertData(person: Person) {
        personDao.insertData(person)
    }

    suspend fun updateData(person: Person) {
        personDao.updateData(person)
    }

    suspend fun deleteData(person: Person) {
        personDao.deleteData(person)
    }

    suspend fun deleteAllData() {
        personDao.deleteAllData()
    }


    fun searchDatabase(searchQuery: String): Flow<List<Person>> {
        return personDao.searchDatabase(searchQuery)
    }

    fun orderByAgeDesc():Flow<List<Person>>{
        return personDao.orderByAgeDesc()
    }
    fun sortByNames():Flow<List<Person>>{
        return personDao.sortByNames()
    }
}