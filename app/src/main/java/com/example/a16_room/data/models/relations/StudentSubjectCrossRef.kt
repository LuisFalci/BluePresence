package com.example.a16_room.data.models.relations

import androidx.room.Entity


@Entity(primaryKeys = ["id", "subjectId"])
class StudentSubjectCrossRef(
    val id: Long,
    val subjectId: Long
)