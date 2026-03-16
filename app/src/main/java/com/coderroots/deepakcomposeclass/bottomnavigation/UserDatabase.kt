package com.coderroots.deepakcomposeclass.bottomnavigation

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        var userDatabase: UserDatabase?=null
        fun getInstance(context: Context): UserDatabase?{
           // Synchronized(this){
                if(userDatabase == null) {
                    userDatabase = Room.databaseBuilder(
                        context,
                        UserDatabase::class.java,
                        "user_database"
                    )
                        .build()

           //     }
            }
            return userDatabase
        }

    }
}