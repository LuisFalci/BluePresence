package com.example.a16_room.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.repository.StudentRepository

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudentRepository(application.applicationContext)

    private val listStudents = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> = listStudents

    private val studentModel = MutableLiveData<StudentModel>()
    var student: LiveData<StudentModel> = studentModel

    private var changes = MutableLiveData<Long>()
    var newChange: LiveData<Long> = changes

    fun getAll() {
        listStudents.value = repository.getAll()
    }

    fun get(id: Int) {
        studentModel.value = repository.get(id)
    }

    fun insert(name: String, registration: String) {
        val model = StudentModel().apply {
            this.name = name
            this.registration = registration
        }
        changes.value = repository.insert(model)
    }

    fun update(id: Int, name: String, registration: String) {
        val model = StudentModel().apply {
            this.id = id
            this.name = name
            this.registration = registration
        }
        changes.value = repository.update(model).toLong()
    }

    fun delete(id: Int) {
        val model = StudentModel().apply {
            this.id = id
        }
        changes.value = repository.delete(model).toLong()
    }
}