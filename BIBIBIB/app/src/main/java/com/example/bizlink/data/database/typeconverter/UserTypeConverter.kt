package com.example.bizlink.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.bizlink.ui.entities.User
import com.google.gson.Gson

class UserTypeConverter {

    @TypeConverter
    fun fromUserToDbEntity(userData: User): String {
        val resultStr = StringBuilder().apply {
            this.append(userData.id)
            this.append(';')
            this.append(userData.email.toString())
            this.append(';')
            this.append(userData.photo.toString(Charsets.UTF_8))
            this.append(';')
        }
        return resultStr.toString()
    }

    @TypeConverter
    fun fromDbEntityToUserModel(input: String): User {
        var index: Int = Integer.parseInt(input.substringBefore(';'))
        val email: String = input.substringBefore(';')
        val photo:ByteArray = input.substringBefore(';').toByteArray(Charsets.UTF_8)
        return User(
            id = index,
            email = email,
            photo = photo
        )
    }

}