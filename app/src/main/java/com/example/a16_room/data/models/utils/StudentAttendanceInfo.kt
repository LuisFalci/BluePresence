package com.example.a16_room.data.models.utils

data class StudentAttendanceInfo(
    val studentName: String,
    val totalAbsences: Int,
    val totalPresences: Int,
    val totalStudents: Int,
    val attendancePercentage: Double,
)
