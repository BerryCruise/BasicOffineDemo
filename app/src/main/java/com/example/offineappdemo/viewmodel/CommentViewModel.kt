package com.example.offineappdemo.viewmodel

import android.arch.lifecycle.*
import com.example.offineappdemo.model.Comment
import com.example.offineappdemo.model.Resource
import com.example.offineappdemo.repository.CommentRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CommentViewModel @Inject constructor(private val commentRepository: CommentRepository) : ViewModel() {
    private val postIdLiveData = MutableLiveData<Int>()
    private val commentsLiveData = Transformations.switchMap(postIdLiveData) {
        commentRepository.getComments(it)
    }
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    fun comments(): LiveData<Resource<List<Comment>>> {
        return commentsLiveData
    }

    fun loadComments(postId: Int) {
        postIdLiveData.value = postId
    }
}