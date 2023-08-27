package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.StudentDatabase
import com.example.a16_room.data.models.StudentModel

class StudentRepository(context: Context) {

    // Instanciamos o database, e com ele temos acesso a DAO que é onde estão implementadas as funções de fato
    private val studenDatabase = StudentDatabase.getDatabase(context).studentDAO()
    fun insert(student: StudentModel): Long {
        return studenDatabase.insert(student)
    }

    fun update(student: StudentModel): Int {
        return studenDatabase.update(student)
    }

    fun delete(student: StudentModel): Int {
        return studenDatabase.delete(student)
    }

    fun get(id: Int): StudentModel {
        return studenDatabase.get(id)
    }

    fun getAll(): List<StudentModel> {
        return studenDatabase.getAll()
    }
}