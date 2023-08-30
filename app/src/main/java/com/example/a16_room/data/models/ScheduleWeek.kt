package com.example.a16_room.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ScheduleWeek")

class ScheduleWeek {
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long = 0

    var subjectId: Int = 0

    @ColumnInfo(name = "startTime")
    val startTime: String = ""

    @ColumnInfo(name = "endTime")
    val endTime: String = ""

    @ColumnInfo(name = "dayOfWeek")
    var dayOfWeek: String = ""
}