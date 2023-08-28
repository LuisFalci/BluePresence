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

    fun get(id: Long) {
        studentModel.value = repository.get(id)
    }

    fun insert(name: String, registration: String): Long {
        val model = StudentModel().apply {
            this.name = name
            this.registration = registration
        }
        return repository.insert(model)
    }

    fun update(id: Long, name: String, registration: String): Int {
        val model = StudentModel().apply {
            this.id = id
            this.name = name
            this.registration = registration
        }
        val affectedRows = repository.update(model)
        changes.value = affectedRows.toLong()
        return affectedRows
    }

    fun delete(id: Long): Int {
        val model = StudentModel().apply {
            this.id = id
        }
        val affectedRows = repository.delete(model)
        changes.value = affectedRows.toLong()
        return affectedRows
    }
    fun getAllStudentsInSubject(subjectId: Int) {
        listStudents.value = repository.getAllStudentsInSubject(subjectId)
    }
}