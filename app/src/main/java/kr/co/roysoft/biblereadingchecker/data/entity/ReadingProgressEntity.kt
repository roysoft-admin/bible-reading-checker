package kr.co.roysoft.biblereadingchecker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_progress")
data class ReadingProgressEntity(
    @PrimaryKey
    val day: Int,
    val planType: String,
    val passage: String,
    val completed: Boolean = false,
    val completedDate: Long? = null
)

