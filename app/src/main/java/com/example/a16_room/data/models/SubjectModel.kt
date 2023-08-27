package com.example.a16_room.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subject")
class SubjectModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subject_id")
    var subjectId: Int = 0

    @ColumnInfo(name = "subject_name")
    var subjectName: String = ""
}