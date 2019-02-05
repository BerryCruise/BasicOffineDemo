package com.example.offineappdemo.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.offineappdemo.api.CommentAPI
import com.example.offineappdemo.data.CommentDao
import com.example.offineappdemo.model.Comment
import com.example.offineappdemo.model.Resource
import com.example.offineappdemo.util.AppExecutors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentRepository @Inject constructor(
        private val commentDao: CommentDao,
        private val commentAPI: CommentAPI,
        private val appExecutors: AppExecutors) {

    fun getComments(postId: Int): LiveData<Resource<List<Comment>>> {
        return object : NetworkBoundResource<List<Comment>, List<Comment>>(appExecutors) {

            override fun saveCallResult(item: List<Comment>) {
                commentDao.insert(item)
            }

            override fun shouldFetch(data: List<Comment>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Comment>> = commentDao.getComments(postId)


            override fun createCall(): LiveData<Resource<List<Comment>>> {
                val commentsLiveData = MutableLiveData<Resource<List<Comment>>>()
                commentAPI
                        .getComments(postId.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<Comment>>() {

                            override fun onSuccess(t: List<Comment>) {
                                commentsLiveData.value = Resource.success(t)
                            }

                            override fun onError(e: Throwable) {
                                commentsLiveData.value = Resource.error(null, e.message)
                            }
                        })
                return commentsLiveData
            }
        }.asLiveData()
    }
}