package com.example.a16_room.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
class SubjectModel {
    @PrimaryKey(autoGenerate = true)
    var subjectId: Long = 0

    var subjectName: String = ""
}