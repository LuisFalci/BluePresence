package com.example.a16_room.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance",
    foreignKeys = [ForeignKey(
        entity = StudentModel::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE // Defina o comportamento de exclusão desejado
    )])
class AttendanceModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attendanceId")
    var attendanceId: Long = 0

    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "presence")
    var presence: Boolean = false

    @ColumnInfo(name = "attendanceTimestamp")
    var attendanceTimestamp: String = ""
}
