package com.example.offineappdemo.view

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

import com.example.offineappdemo.model.Comment

class CommentAdapter(private val comments: MutableList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        comments[position].let {
            (holder as CommentViewHolder).bindTo(it)
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun updateCommentList(newComments: List<Comment>) {
        this.comments.clear()
        this.comments.addAll(newComments)
        notifyDataSetChanged()
    }
}