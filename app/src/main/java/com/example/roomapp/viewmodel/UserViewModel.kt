package com.example.roomapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.UserDatabase
import com.example.roomapp.model.User
import com.example.roomapp.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository
    lateinit var userSave : User

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun getUser(name: String) : User{
        var boolean = true
        userSave = User("",0f,0f,"")
        viewModelScope.launch(Dispatchers.IO) {
           try {
               Log.d("getuser","1")
               userSave = repository.getUser(name)
           }catch (exception: Exception){
               boolean = false
               Log.d("getuser","2")
               userSave = User("",0f,0f,"")
           }
            boolean = false
        }
        while (boolean){}
        return userSave
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

}