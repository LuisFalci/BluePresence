package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.StudentModel

class StudentRepository(context: Context) {

    private val studentDatabase = AppDatabase.getDatabase(context).studentDAO()
    fun insert(student: StudentModel): Long {
        return studentDatabase.insert(student)
    }

    fun update(student: StudentModel): Int {
        return studentDatabase.update(student)
    }

    fun delete(student: StudentModel): Int {
        return studentDatabase.delete(student)
    }

    fun get(id: Long): StudentModel {
        return studentDatabase.get(id)
    }

    fun getAll(): List<StudentModel> {
        return studentDatabase.getAll()
    }
    fun getAllStudentsInSubject(subjectId: Long): List<StudentModel> {
        return studentDatabase.getAllStudentsInSubject(subjectId)
    }
}