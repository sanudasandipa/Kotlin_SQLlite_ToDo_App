package com.example.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(val name:String , val age:String){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}