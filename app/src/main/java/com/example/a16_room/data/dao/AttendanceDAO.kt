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
    fun insert(attendance: AttendanceModel): Long

    @Update
    fun update(attendance: AttendanceModel): Int

    @Delete
    fun delete(attendance: AttendanceModel): Int

    @Query("SELECT * FROM Attendance WHERE attendanceId = :attendanceId")
    fun get(attendanceId: Long): AttendanceModel

    @Query("SELECT * FROM Attendance")
    fun getAll(): List<AttendanceModel>

    @Query("SELECT * FROM Attendance WHERE id = :studentId")
    fun getAllAttendancesForStudent(studentId: Long): List<AttendanceModel>
}