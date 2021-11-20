package com.example.mygithubapplication.ViewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygithubapplication.DataBase.RoomAppDb
import com.example.mygithubapplication.DataBase.UserEntity

class DataBaseViewModel(app: Application) : AndroidViewModel(app) {
    var allUsers: MutableLiveData<List<UserEntity>>

    init {
        allUsers = MutableLiveData()
        getAllUsers()
    }

    fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }

    fun getAllUsers() {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()

        allUsers.postValue(list)
    }

    fun updateUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.updateUser(entity)
        getAllUsers()
    }

    fun deleteUserInfo(entity: UserEntity) {
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.deleteUser(entity)
        getAllUsers()
    }

}