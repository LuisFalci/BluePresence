package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a16_room.data.models.AttendanceModel
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

    @Query(
        "SELECT Attendance.* FROM Attendance " +
                "INNER JOIN StudentAttendanceCrossRef ON Attendance.attendanceId = StudentAttendanceCrossRef.attendanceId " +
                "WHERE StudentAttendanceCrossRef.subjectId = :subjectId"
    )
    fun getAllAttendancesFromSubject(subjectId: Long): List<AttendanceModel>

}