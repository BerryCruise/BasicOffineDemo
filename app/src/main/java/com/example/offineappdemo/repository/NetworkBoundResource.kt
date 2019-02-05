package com.example.offineappdemo.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.example.offineappdemo.model.Resource
import com.example.offineappdemo.model.Status
import com.example.offineappdemo.util.AppExecutors

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread internal constructor(private var appExecutors: AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.value = Resource.success(newData) }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) { newData -> result.value = Resource.loading(newData) }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            if (response?.status == Status.SUCCESS) {
                response.data?.let { saveResultAndReInit(it) }
            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData ->
                    result.value = Resource.error(newData, response?.message)
                }
            }
        }
    }

    @MainThread
    private fun saveResultAndReInit(item: RequestType) {
        appExecutors
                .diskIO()
                .execute {
                    saveCallResult(item)
                    appExecutors
                            .mainThread()
                            .execute {
                                val dbSource = loadFromDb()
                                result.addSource(dbSource) { newData ->
                                    result.value = Resource.success(newData)
                                }
                            }
                }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>
}