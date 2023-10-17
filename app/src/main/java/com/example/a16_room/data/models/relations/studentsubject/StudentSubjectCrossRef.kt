package com.example.a16_room.data.models.relations.studentsubject

import androidx.room.Entity


@Entity(primaryKeys = ["studentId", "subjectId"])
class StudentSubjectCrossRef(
    val studentId: Long,
    val subjectId: Long
)