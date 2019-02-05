package com.example.offineappdemo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Comment(var postId: Long = 0,
                   @SerializedName("body")
                   var content: String = "") {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var isSyncPending: Boolean = false

    init {
        this.isSyncPending = true
    }

    override fun toString(): String {
        return String.format("Comment id: %s, text: %s, syncPending: %s", id, content, isSyncPending)
    }
}