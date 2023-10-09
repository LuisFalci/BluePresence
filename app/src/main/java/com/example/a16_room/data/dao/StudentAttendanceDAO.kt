package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.relations.studentattendance.StudentAttendanceCrossRef
import com.example.a16_room.data.models.utils.StudentAttendanceInfo

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

    @Query(
        "SELECT Student.name AS studentName, " +
                "SUM(CASE WHEN StudentAttendanceCrossRef.isPresent THEN 1 ELSE 0 END) AS totalPresences, " +
                "SUM(CASE WHEN NOT StudentAttendanceCrossRef.isPresent THEN 1 ELSE 0 END) AS totalAbsences, " +
                "(SUM(CASE WHEN StudentAttendanceCrossRef.isPresent THEN 1 ELSE 0 END) * 100.0) / " +
                "(COUNT(StudentAttendanceCrossRef.studentId) * 1.0) AS attendancePercentage, " +
                "SUM(CASE WHEN StudentAttendanceCrossRef.isPresent THEN 1 ELSE 0 END) + " +
                "SUM(CASE WHEN NOT StudentAttendanceCrossRef.isPresent THEN 1 ELSE 0 END) AS totalStudents " +
                "FROM Student " +
                "INNER JOIN StudentAttendanceCrossRef ON Student.studentId = StudentAttendanceCrossRef.studentId " +
                "INNER JOIN Attendance ON StudentAttendanceCrossRef.attendanceId = Attendance.attendanceId " +
                "WHERE Attendance.subjectId = :subjectId " +
                "GROUP BY Student.studentId"
    )
    fun getStudentAttendanceInfo(subjectId: Long): List<StudentAttendanceInfo>
}