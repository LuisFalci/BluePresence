package com.example.a16_room.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance",
    foreignKeys = [
        ForeignKey(entity = SubjectModel::class,
            parentColumns = ["subjectId"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE) // Adicione esta linha para realizar a exclusão em cascata
    ])
class AttendanceModel() {
    @PrimaryKey(autoGenerate = true)
    var attendanceId: Long = 0
    var subjectId: Long = 0
    var dateTime: Long = 0
    var totalStudents: Int = 0
    var totalPresents: Int = 0
}