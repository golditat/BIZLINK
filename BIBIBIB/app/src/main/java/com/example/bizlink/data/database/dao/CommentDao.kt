package com.example.bizlink.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bizlink.data.database.entity.CommentEntity
import com.example.bizlink.data.database.entity.MaterialEntity
import com.example.bizlink.data.database.entity.ProjectEntity

@Dao
interface CommentDao {
    @Query("SELECT * FROM comment WHERE id LIKE :id")
    fun getCommentById(id: Int): CommentEntity?

    @Query("DELETE FROM comment WHERE id == :id")
    fun deleteComment(id:Int)

    @Query("DELETE FROM comment")
    fun deleteAllComments()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(commentData: CommentEntity)

    @Query("SELECT * FROM comment")
    fun getAllComments(): List<CommentEntity>?
}