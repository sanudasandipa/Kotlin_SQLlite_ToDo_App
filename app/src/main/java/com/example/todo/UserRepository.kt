package com.example.todo

import android.content.Context
import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserRepository {
    companion object{

        private var userDatabase:UserDatabase?=null
        fun initializeDB(context: Context):UserDatabase?{
            return UserDatabase.getInstance(context)
        }

        fun insert(context: Context,user: User){
            userDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                userDatabase?.getDao()?.insert(user)
            }

        }
        fun getAllUserData(context: Context): LiveData<List<User>>? {
            userDatabase= initializeDB(context)
            return userDatabase?.getDao()?.getAllUserData()

        }
    }
}