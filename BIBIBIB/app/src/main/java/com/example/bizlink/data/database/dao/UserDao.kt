package com.example.bizlink.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email LIKE :email")
    fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM user WHERE id LIKE :id")
    fun getUserByID(id: Int):UserEntity?

    @Query("DELETE FROM user WHERE id == :id")
    fun deleteUser(id:Int)

    @Update(entity = UserEntity::class)
    fun updateUserInfo(user: UserEntity)

    @Query("DELETE FROM user")
    fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userData: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<UserEntity>?
}