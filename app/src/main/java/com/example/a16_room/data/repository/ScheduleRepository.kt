package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.StudentDatabase
import com.example.a16_room.data.models.ScheduleModel

class ScheduleRepository(context: Context) {
    private val scheduleDatabase = StudentDatabase.getDatabase(context).scheduleDAO()

    fun insert(scheduleModel: ScheduleModel): Long {
        return scheduleDatabase.insert(scheduleModel)
    }

    fun get(scheduleId: Long): ScheduleModel {
        return scheduleDatabase.get(scheduleId)
    }

    fun getAllSchedulesForSubject(subjectId: Int): List<ScheduleModel> {
        return scheduleDatabase.getAllSchedulesForSubject(subjectId)
    }
}
