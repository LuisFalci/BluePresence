package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.data.models.relations.StudentSubjectCrossRef

@Dao
interface SubjectDAO {
    @Insert
    fun insert(subject: SubjectModel): Long

    @Insert
    fun insertStudentSubjectCrossRef(studentSubjectCrossRef: StudentSubjectCrossRef)

    @Update
    fun update(subject: SubjectModel): Int

    @Delete
    fun delete(subject: SubjectModel): Int

    @Query("SELECT * FROM Subject WHERE subject_id = :id")
    fun get(id: Int): SubjectModel

    @Query("SELECT * FROM Subject")
    fun getAll(): List<SubjectModel>

    @Query(
        "SELECT Student.* FROM Student " +
                "INNER JOIN StudentSubjectCrossRef ON Student.id = StudentSubjectCrossRef.id " +
                "WHERE StudentSubjectCrossRef.subjectId = :subjectId"
    )
    fun getAllStudentsInSubject(subjectId: Int): List<StudentModel>


}
