package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a16_room.data.models.StudentModel

@Dao
interface StudentDAO {
    @Insert
    fun insert(student: StudentModel): Long

    @Update
    fun update(student: StudentModel): Int

    @Delete
    fun delete(student: StudentModel): Int

    @Query("SELECT * FROM Student WHERE id = :id")
    fun get(id: Long): StudentModel

    @Query("SELECT * FROM Student")
    fun getAll(): List<StudentModel>

    @Query(
        "SELECT Student.* FROM Student " +
                "INNER JOIN StudentSubjectCrossRef ON Student.id = StudentSubjectCrossRef.id " +
                "WHERE StudentSubjectCrossRef.subjectId = :subjectId"
    )
    fun getAllStudentsInSubject(subjectId: Long): List<StudentModel>

}