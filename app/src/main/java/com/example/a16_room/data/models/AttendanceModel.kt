package com.example.a16_room.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance",
    foreignKeys = [ForeignKey(
        entity = StudentModel::class,
        parentColumns = ["studentId"],
        childColumns = ["student_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class AttendanceModel {
    @PrimaryKey(autoGenerate = true)
    var attendanceId: Long = 0

    var student_id: Long = 0

    var presence: Boolean = false

    var dateTime: String = ""
}
