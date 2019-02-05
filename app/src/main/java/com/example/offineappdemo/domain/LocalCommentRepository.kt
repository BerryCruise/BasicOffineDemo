package com.example.offineappdemo.domain

import com.example.offineappdemo.model.Comment
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface LocalCommentRepository {
    fun add(photoId: Long, commentText: String): Single<Comment>
    fun update(comment: Comment): Completable
    fun delete(comment: Comment): Completable
    fun getComments(photoId: Long): Flowable<List<Comment>>
}