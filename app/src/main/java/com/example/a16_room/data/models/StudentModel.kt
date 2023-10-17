package com.example.a16_room.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
class StudentModel {
    @PrimaryKey(autoGenerate = true)
    var studentId: Long = 0

    var name: String = ""

    var registration: String = ""

    var macAddress: String = ""
}