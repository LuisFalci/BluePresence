package com.example.a16_room.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.a16_room.data.models.AttendanceModel

@Dao
interface AttendanceDAO {
    @Insert
    fun insert(attendanceModel: AttendanceModel): Long

    @Update
    fun update(attendance: AttendanceModel): Int

    @Delete
    fun delete(attendance: AttendanceModel): Int

    @Query("DELETE FROM StudentAttendanceCrossRef WHERE attendanceId = :attendanceId")
    fun deleteStudentAttendanceCrossRefByAttendanceId(attendanceId: Long)

    @Query("SELECT * FROM Attendance")
    fun getAll(): List<AttendanceModel>

    @Query("SELECT * FROM Attendance WHERE attendanceId IN (SELECT attendanceId FROM StudentAttendanceCrossRef WHERE subjectId = :subjectId)")
    fun getAllAttendancesFromSubject(subjectId: Long): List<AttendanceModel>

    @Query("SELECT * FROM Attendance WHERE attendanceId = :attendanceId")
    fun getAttendance(attendanceId: Long): AttendanceModel
}