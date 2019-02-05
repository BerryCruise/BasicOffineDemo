package com.example.offineappdemo.api

import com.example.offineappdemo.model.Comment
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentAPI @Inject constructor(private var retrofit: Retrofit) {

    private val commentService by lazy {
        retrofit.create(CommentService::class.java)
    }

    fun getComments(postId: String): Single<List<Comment>> = commentService.getComments(postId)

    fun addComments(comment: Comment): Completable = commentService.addComments(comment)

    interface CommentService {

        @GET("comments")
        fun getComments(@Query("postId") postId: String): Single<List<Comment>>

        @POST("posts")
        fun addComments(@Body comment: Comment): Completable
    }
}