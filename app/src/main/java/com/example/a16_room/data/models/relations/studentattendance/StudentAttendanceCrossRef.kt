package com.example.a16_room.data.models.relations.studentattendance

import androidx.room.Entity

@Entity(primaryKeys = ["attendanceId", "subjectId", "studentId"])
class StudentAttendanceCrossRef(
    val attendanceId: Long,
    val subjectId: Long,
    val studentId: Long,
    val isPresent: Boolean = false
)