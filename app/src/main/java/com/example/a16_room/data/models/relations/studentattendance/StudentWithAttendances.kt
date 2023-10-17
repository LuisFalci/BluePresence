package com.example.a16_room.data.models.relations.studentattendance

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel

class StudentWithAttendances {
    @Embedded
    lateinit var student: StudentModel

    @Relation(
        parentColumn = "studentId",
        entityColumn = "attendanceId",
        associateBy = Junction(StudentAttendanceCrossRef::class)
    )
    lateinit var attendances: List<AttendanceModel>

    @Relation(
        parentColumn = "studentId",
        entityColumn = "subjectId",
        associateBy = Junction(StudentAttendanceCrossRef::class)
    )
    lateinit var subjects: List<SubjectModel>
}
