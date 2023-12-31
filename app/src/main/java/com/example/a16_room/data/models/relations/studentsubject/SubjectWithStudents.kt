package com.example.a16_room.data.models.relations.studentsubject

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel

class SubjectWithStudents {
    @Embedded
    lateinit var subject: SubjectModel

    @Relation(
        parentColumn = "subjectId",
        entityColumn = "studentId",
        associateBy = Junction(StudentSubjectCrossRef::class)
    )
    lateinit var student: List<StudentModel>
}