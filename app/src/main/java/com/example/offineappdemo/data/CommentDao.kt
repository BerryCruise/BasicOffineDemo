package com.example.offineappdemo.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.offineappdemo.model.Comment

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comment: Comment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(commentListing: List<Comment>)

    @Update
    fun update(comment: Comment)

    @Delete
    fun delete(comment: Comment)

    @Query("SELECT * FROM comment WHERE postId = :postId ORDER BY id DESC")
    fun getComments(postId: Int): LiveData<List<Comment>>
}