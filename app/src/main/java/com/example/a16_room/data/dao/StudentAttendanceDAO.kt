package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.relations.studentattendance.StudentAttendanceCrossRef

@Dao
interface StudentAttendanceDAO {
    @Insert
    fun insert(studentAttendanceCrossRef: StudentAttendanceCrossRef)

    @Update
    fun update(studentAttendanceCrossRef: StudentAttendanceCrossRef): Int

    @Delete
    fun delete(studentAttendanceCrossRef: StudentAttendanceCrossRef): Int

    @Query(
        "SELECT Student.* FROM Student " +
                "INNER JOIN StudentAttendanceCrossRef ON Student.studentId = StudentAttendanceCrossRef.studentId " +
                "WHERE StudentAttendanceCrossRef.attendanceId = :attendanceId"
    )
    fun getAllStudentsFromAttendance(attendanceId: Long): List<StudentModel>

    @Query("SELECT * FROM StudentAttendanceCrossRef WHERE attendanceId = :attendanceId AND subjectId = :subjectId")
    fun getPresences(attendanceId: Long, subjectId: Long): List<StudentAttendanceCrossRef>

}