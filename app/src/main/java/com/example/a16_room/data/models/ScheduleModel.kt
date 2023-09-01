package com.example.a16_room.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Schedule",
        foreignKeys = [
            ForeignKey(
                    entity = SubjectModel::class,
                    parentColumns = ["subject_id"],
                    childColumns = ["subjectId"],
                    onDelete = ForeignKey.CASCADE // Define a ação de exclusão em cascata
            )
        ])

class ScheduleModel {
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long = 0

    var subjectId: Long = 0

    @ColumnInfo(name = "startTime")
    var startTime: String = ""

    @ColumnInfo(name = "endTime")
    var endTime: String = ""

    @ColumnInfo(name = "dayOfWeek")
    var dayOfWeek: String = ""
}