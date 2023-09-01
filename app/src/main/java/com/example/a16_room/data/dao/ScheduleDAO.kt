package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.a16_room.data.models.ScheduleModel

@Dao
interface ScheduleDAO {
    @Insert
    fun insert(scheduleModel: ScheduleModel)

    @Query("SELECT * FROM Schedule WHERE scheduleId = :scheduleId")
    fun get(scheduleId: Long): ScheduleModel

    @Query("SELECT * FROM Schedule WHERE subjectId = :subjectId")
    fun getAllSchedulesForSubject(subjectId: Long): List<ScheduleModel>
}