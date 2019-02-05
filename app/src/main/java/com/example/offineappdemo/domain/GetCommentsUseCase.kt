package com.example.offineappdemo.domain

import com.example.offineappdemo.model.Comment
import com.example.offineappdemo.repository.CommentRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val commentRepository: CommentRepository) {

//    val comments: Flowable<List<Comment>>
//        get() = localCommentRepository.getComments(ModelConstants.DUMMY_PHOTO_ID)
}
