package com.example.a16_room.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.data.repository.SubjectRepository

class SubjectViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SubjectRepository(application.applicationContext)

    private val listSubjects = MutableLiveData<List<SubjectModel>>()
    val subjects: LiveData<List<SubjectModel>> = listSubjects

    private val subjectModel = MutableLiveData<SubjectModel>()
    var subject: LiveData<SubjectModel> = subjectModel

    private var changes = MutableLiveData<Long>()
    var newChange: LiveData<Long> = changes

    fun getAll() {
        listSubjects.value = repository.getAll()
    }

    fun get(id: Int) {
        subjectModel.value = repository.get(id)
    }
    fun getAllStudentsInSubject(subjectId: Int): List<StudentModel> {
        return repository.getAllStudentsInSubject(subjectId)
    }

    fun insert(subjectName: String) {
        val model = SubjectModel().apply {
            this.subjectName = subjectName
        }
        changes.value = repository.insert(model)
    }

    fun insertStudentSubject(studentId: Int, subjectId: Int) {
        repository.insertStudentSubject(studentId, subjectId)
    }

    fun update(id: Int, subjectName: String) {
        val model = SubjectModel().apply {
            this.subjectId = id
            this.subjectName = subjectName
        }
        changes.value = repository.update(model).toLong()
    }

    fun delete(id: Int) {
        val model = SubjectModel().apply {
            this.subjectId = id
        }
        changes.value = repository.delete(model).toLong()
    }
}
