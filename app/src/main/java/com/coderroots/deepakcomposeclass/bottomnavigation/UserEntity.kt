package com.coderroots.deepakcomposeclass.bottomnavigation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var userName: String? = null,
    var contactNumber: String?=null
)
