package com.example.firebaseuiauthenticationmarkusneuhoff.data

import androidx.room.*
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM person_table ORDER BY id ASC")
    fun readData(): Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(person: Person)

    @Delete
    suspend fun deleteData(person: Person)

    @Update
    suspend fun updateData(person: Person)

    @Query("DELETE FROM person_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM person_table WHERE firstName LIKE :searchQuery OR lastName LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Person>>

    //order by age desc
    @Query("SELECT * FROM person_table ORDER BY age DESC")
    fun orderByAgeDesc():Flow<List<Person>>

    //sortByNames
    @Query("SELECT * FROM person_table ORDER BY lastName,firstName")
    fun sortByNames():Flow<List<Person>>
}