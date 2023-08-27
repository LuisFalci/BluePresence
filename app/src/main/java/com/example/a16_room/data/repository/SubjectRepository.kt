package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.StudentDatabase
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.data.models.relations.StudentSubjectCrossRef

class SubjectRepository(context: Context) {
    private val subjectDAO = StudentDatabase.getDatabase(context).subjectDAO()

    fun insert(subject: SubjectModel): Long {
        return subjectDAO.insert(subject)
    }

    fun insertStudentSubject(studentId: Int, subjectId: Int) {
        val studentSubjectCrossRef = StudentSubjectCrossRef(studentId, subjectId)
        subjectDAO.insertStudentSubjectCrossRef(studentSubjectCrossRef)
    }

    fun update(subject: SubjectModel): Int {
        return subjectDAO.update(subject)
    }

    fun delete(subject: SubjectModel): Int {
        return subjectDAO.delete(subject)
    }

    fun get(id: Int): SubjectModel {
        return subjectDAO.get(id)
    }

    fun getAll(): List<SubjectModel> {
        return subjectDAO.getAll()
    }
    fun getAllStudentsInSubject(subjectId: Int): List<StudentModel> {
        return subjectDAO.getAllStudentsInSubject(subjectId)
    }

}
