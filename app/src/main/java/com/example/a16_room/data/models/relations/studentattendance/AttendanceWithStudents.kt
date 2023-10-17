package com.example.a16_room.data.models.relations.studentattendance

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel

class AttendanceWithStudents {
    @Embedded
    lateinit var attendance: AttendanceModel

    @Relation(
        parentColumn = "attendanceId",
        entityColumn = "studentId",
        associateBy = Junction(StudentAttendanceCrossRef::class)
    )
    lateinit var students: List<StudentModel>

    @Relation(
        parentColumn = "attendanceId",
        entityColumn = "subjectId",
        associateBy = Junction(StudentAttendanceCrossRef::class)
    )
    lateinit var subjects: List<SubjectModel>
}
