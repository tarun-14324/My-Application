package com.example.mygithubapplication.DataBase

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.mygithubapplication.dataSource.RecyclerData


@Dao
interface UserDao {


    @Query("SELECT * FROM userinfo ORDER BY Id DESC LIMIT 15")
    fun getAllUserInfo(): List<UserEntity>?


    @Insert
    fun insertUser(user: UserEntity?)

    @Delete
    fun deleteUser(user: UserEntity?)

    @Update
    fun updateUser(user: UserEntity?)


}