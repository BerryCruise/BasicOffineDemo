package com.example.offineappdemo.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.offineappdemo.R
import com.example.offineappdemo.di.Injectable
import com.example.offineappdemo.domain.Constants
import com.example.offineappdemo.viewmodel.CommentViewModel
import kotlinx.android.synthetic.main.activity_comments.*
import javax.inject.Inject

class CommentsActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var commentViewModel: CommentViewModel
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        init()
        initUI()
        initAction()
        loadData()
    }

    private fun init() {
        commentViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(CommentViewModel::class.java)
    }

    private fun initUI() {
        commentAdapter = CommentAdapter(mutableListOf())
        val mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = commentAdapter
    }

    private fun initAction() {
        commentViewModel
                .comments()
                .observe(this, Observer { t ->
                    t?.let {
                        if (it.data != null) {
                            commentAdapter.updateCommentList(it.data)
                            commentAdapter.notifyDataSetChanged()
                        }
                    }
                })
    }

    private fun loadData() {
        commentViewModel.loadComments(Constants.DUMMY_POST_ID)
    }
}