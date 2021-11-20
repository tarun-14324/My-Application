package com.example.mygithubapplication.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userinfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "num") val num: String
)
