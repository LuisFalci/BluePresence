package com.example.a16_room.data.models.relations.studentsubject

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel

class StudentWithSubjects {
    @Embedded
    lateinit var student: StudentModel

    @Relation(
        parentColumn = "studentId",
        entityColumn = "subjectId",
        associateBy = Junction(StudentSubjectCrossRef::class)
    )
    lateinit var subjects: List<SubjectModel>
}
