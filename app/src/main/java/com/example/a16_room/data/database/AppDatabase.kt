package com.example.a16_room.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.a16_room.data.dao.ScheduleDAO
import com.example.a16_room.data.dao.StudentDAO
import com.example.a16_room.data.dao.SubjectDAO
import com.example.a16_room.data.models.ScheduleModel
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.data.models.relations.StudentSubjectCrossRef

@Database(entities = [StudentModel::class, SubjectModel::class, StudentSubjectCrossRef::class, ScheduleModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDAO(): StudentDAO
    abstract fun subjectDAO(): SubjectDAO

    abstract fun scheduleDAO(): ScheduleDAO

    //PADRÃO SINGLETON (impedimos instanciar mais de um banco)
    companion object {
        private lateinit var INSTANCE: AppDatabase

        fun getDatabase(context: Context): AppDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "bluePresenceDB")
                            .addMigrations(MIGRATION)
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

        private val MIGRATION: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //IMPLEMENTAR O NECESSÁRIO
            }

        }

    }
}