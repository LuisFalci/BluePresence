package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.data.models.relations.StudentSubjectCrossRef

class SubjectRepository(context: Context) {
    private val subjectDAO = AppDatabase.getDatabase(context).subjectDAO()

    fun insert(subject: SubjectModel): Long {
        return subjectDAO.insert(subject)
    }

    fun insertStudentSubject(studentId: Long, subjectId: Long) {
        val studentSubjectCrossRef = StudentSubjectCrossRef(studentId, subjectId)
        subjectDAO.insertStudentSubjectCrossRef(studentSubjectCrossRef)
    }

    fun update(subject: SubjectModel): Int {
        return subjectDAO.update(subject)
    }

    fun delete(subject: SubjectModel): Int {
        return subjectDAO.delete(subject)
    }

    fun get(id: Long): SubjectModel {
        return subjectDAO.get(id)
    }

    fun getAll(): List<SubjectModel> {
        return subjectDAO.getAll()
    }
    fun getAllStudentsInSubject(subjectId: Long): List<StudentModel> {
        return subjectDAO.getAllStudentsInSubject(subjectId)
    }

}
