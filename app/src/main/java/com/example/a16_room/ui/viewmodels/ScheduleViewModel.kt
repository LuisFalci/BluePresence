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

    fun getAllSchedulesForSubject(subjectId: Int) {
        listSchedules.value = repository.getAllSchedulesForSubject(subjectId)
    }

    fun insert(startTime: String, endTime: String, dayOfWeek: String): Long {
        val model = ScheduleModel().apply {
            this.startTime = startTime
            this.endTime = endTime
            this.dayOfWeek = dayOfWeek
        }
        return repository.insert(model)
    }
}
