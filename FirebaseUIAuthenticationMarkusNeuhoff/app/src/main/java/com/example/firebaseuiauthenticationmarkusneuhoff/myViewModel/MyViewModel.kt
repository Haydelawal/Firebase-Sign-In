package com.example.firebaseuiauthenticationmarkusneuhoff.myViewModel

import androidx.lifecycle.*
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Person
import com.example.firebaseuiauthenticationmarkusneuhoff.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class MyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    val readData = repository.readData().asLiveData()


    fun insertData(person: Person){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(person)
        }
    }

    fun updateData(person: Person){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(person)
        }
    }

    fun deleteData(person: Person){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(person)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Person>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }

    fun orderByAgeDesc():LiveData<List<Person>>{
        return repository.orderByAgeDesc().asLiveData()
    }
    fun sortByNames(): LiveData<List<Person>>{
        return repository.sortByNames().asLiveData()
    }

}


