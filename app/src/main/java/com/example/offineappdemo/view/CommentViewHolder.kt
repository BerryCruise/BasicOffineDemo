package com.example.offineappdemo.view

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.offineappdemo.R
import com.example.offineappdemo.model.Comment
import kotlinx.android.synthetic.main.recycler_item_comment.view.*

class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {

        private const val LAYOUT_RES_ID = R.layout.recycler_item_comment

        fun create(parent: ViewGroup): CommentViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(LAYOUT_RES_ID, parent, false)
            return CommentViewHolder(view)
        }
    }

    fun bindTo(comment: Comment) {
        itemView.tvComment.text = comment.content
    }
}