package com.example.bizlink.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.bizlink.ui.entities.Project
import com.example.bizlink.ui.entities.User
import com.google.gson.Gson

class ProjectTypeConverter {
    @TypeConverter
    fun fromProjectToDbEntity(projectData: Project): String {
        val resultStr = StringBuilder().apply {
            this.append(projectData.id)
            this.append(';')
            this.append(projectData.projectName)
            this.append(';')
            this.append(projectData.invitingCode)
            this.append(';')
            this.append(projectData.ownerID)
            this.append(';')
        }
        return resultStr.toString()
    }

    @TypeConverter
    fun fromDbEntityToProjectModel(input: String): Project {
        val index: Int = Integer.parseInt(input.substringBefore(';'))
        val projectName: String = input.substringBefore(';')
        val invitingCode:String = input.substringBefore(';')
        val ownerId:Int = Integer.parseInt(input.substringBefore(';'))
        return Project(
            id = index,
            projectName = projectName,
            invitingCode = invitingCode,
            ownerID = ownerId
        )
    }

}