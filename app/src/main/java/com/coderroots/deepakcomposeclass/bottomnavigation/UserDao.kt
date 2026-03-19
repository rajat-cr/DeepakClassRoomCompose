package com.coderroots.deepakcomposeclass.bottomnavigation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert
    fun addUser(userEntity: UserEntity)

    @Query("Select * from UserEntity")
    fun getUsers(): Flow<List<UserEntity>>

    @Update
    fun updateUser(userEntity: UserEntity)
}