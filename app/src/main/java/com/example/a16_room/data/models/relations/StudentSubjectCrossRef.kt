package com.example.a16_room.data.models.relations

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel


@Entity(primaryKeys = ["id", "subjectId"])
class StudentSubjectCrossRef(
    val id: Int,
    val subjectId: Int
)