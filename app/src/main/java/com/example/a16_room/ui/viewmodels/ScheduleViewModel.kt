package com.example.a16_room.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a16_room.data.models.ScheduleModel
import com.example.a16_room.data.repository.ScheduleRepository

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ScheduleRepository(application.applicationContext)

    private val listSchedules = MutableLiveData<List<ScheduleModel>>()
    val schedules: LiveData<List<ScheduleModel>> = listSchedules

    private val scheduleModel = MutableLiveData<ScheduleModel>()
    var schedule: LiveData<ScheduleModel> = scheduleModel

    private var changes = MutableLiveData<Long>()
    var newChange: LiveData<Long> = changes

    fun getAllSchedulesForSubject(subjectId: Long) {
        listSchedules.value = repository.getAllSchedulesForSubject(subjectId)
    }

    fun insert(subjectId: Long, startTime: String, endTime: String, dayOfWeek: String) {
        repository.insert(subjectId, startTime, endTime, dayOfWeek)
    }
}
