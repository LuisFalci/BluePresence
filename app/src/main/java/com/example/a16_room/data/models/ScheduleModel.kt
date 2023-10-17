package com.example.a16_room.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Schedule",
        foreignKeys = [
            ForeignKey(
                    entity = SubjectModel::class,
                    parentColumns = ["subjectId"],
                    childColumns = ["subject_id"],
                    onDelete = ForeignKey.CASCADE // Define a ação de exclusão em cascata
            )
        ])

class ScheduleModel {
    @PrimaryKey(autoGenerate = true)
    var scheduleId: Long = 0

    var subject_id: Long = 0

    var startTime: String = ""

    var endTime: String = ""

    var dayOfWeek: String = ""
}