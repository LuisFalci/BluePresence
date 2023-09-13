package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.ScheduleModel

class ScheduleRepository(context: Context) {
    private val scheduleDAO = AppDatabase.getDatabase(context).scheduleDAO()

    fun insert(subjectId: Long, startTime: String, endTime: String, dayOfWeek: String) {
        val scheduleModel = ScheduleModel()
        scheduleModel.subject_id = subjectId
        scheduleModel.startTime = startTime
        scheduleModel.endTime = endTime
        scheduleModel.dayOfWeek = dayOfWeek
        return scheduleDAO.insert(scheduleModel)
    }

    fun update(schedule: ScheduleModel): Int {
        return scheduleDAO.update(schedule)
    }

    fun delete(schedule: ScheduleModel): Int {
        return scheduleDAO.delete(schedule)
    }

    fun get(scheduleId: Long): ScheduleModel {
        return scheduleDAO.get(scheduleId)
    }

    fun getAllSchedulesForSubject(subjectId: Long): List<ScheduleModel> {
        return scheduleDAO.getAllSchedulesForSubject(subjectId)
    }
}
