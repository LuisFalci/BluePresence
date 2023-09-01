package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.StudentDatabase
import com.example.a16_room.data.models.ScheduleModel

class ScheduleRepository(context: Context) {
    private val scheduleDatabase = StudentDatabase.getDatabase(context).scheduleDAO()

    fun insert(subjectId: Long, startTime: String, endTime: String, dayOfWeek: String) {
        val scheduleModel = ScheduleModel()
        scheduleModel.subjectId = subjectId
        scheduleModel.startTime = startTime
        scheduleModel.endTime = endTime
        scheduleModel.dayOfWeek = dayOfWeek
        return scheduleDatabase.insert(scheduleModel)
    }

    fun get(scheduleId: Long): ScheduleModel {
        return scheduleDatabase.get(scheduleId)
    }

    fun getAllSchedulesForSubject(subjectId: Long): List<ScheduleModel> {
        return scheduleDatabase.getAllSchedulesForSubject(subjectId)
    }
}
