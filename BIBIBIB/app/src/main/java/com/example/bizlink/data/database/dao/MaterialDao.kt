package com.example.bizlink.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bizlink.data.database.entity.MaterialEntity
import com.example.bizlink.data.database.entity.ProjectEntity
import com.example.bizlink.data.database.entity.UserEntity

@Dao
interface MaterialDao {
    @Query("SELECT * FROM material WHERE id LIKE :id")
    fun getMaterialById(id: Int): MaterialEntity?

    @Query("DELETE FROM material WHERE id == :id")
    fun deleteMaterial(id:Int)

    @Query("DELETE FROM material")
    fun deleteAllMaterials()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMaterial(materialData: MaterialEntity)

    @Query("SELECT * FROM material")
    fun getAllMaterials(): List<MaterialEntity>?
}