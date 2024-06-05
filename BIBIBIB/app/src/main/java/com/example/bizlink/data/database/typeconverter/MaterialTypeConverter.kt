package com.example.bizlink.data.database.typeconverter

import androidx.room.TypeConverter
import com.example.bizlink.ui.entities.Material
import com.example.bizlink.ui.entities.Task
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date

class MaterialTypeConverter {
    @TypeConverter
    fun fromMaterialToDbEntity(materialData: Material): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val resultStr = StringBuilder().apply {
            this.append(materialData.id)
            this.append(';')
            this.append(materialData.materialName)
            this.append(';')
            this.append(materialData.taskID)
            this.append(';')
            this.append(materialData.file.toString(Charsets.UTF_8))
            this.append(';')

        }
        return resultStr.toString()
    }

    @TypeConverter
    fun fromDbEntityToTaskModel(input: String): Material {
        val index: Int = Integer.parseInt(input.substringBefore(';'))
        val materialName: String = input.substringBefore(';')
        val taskId:Int = Integer.parseInt(input.substringBefore(';'))
        val file:ByteArray = input.substringBefore(';').toByteArray(Charsets.UTF_8)
        return Material(
            id = index,
            materialName = materialName,
            taskID = taskId,
            file = file
        )
    }

}