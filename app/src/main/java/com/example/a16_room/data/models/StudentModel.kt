package com.example.a16_room.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Student")
class StudentModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "registration")
    var registration: String = ""

    @ColumnInfo(name = "macAddress")
    var macAddress: String = ""
}