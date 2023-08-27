package com.example.a16_room.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.a16_room.data.dao.StudentDAO
import com.example.a16_room.data.dao.SubjectDAO
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.SubjectModel
import com.example.a16_room.data.models.relations.StudentSubjectCrossRef

@Database(entities = [StudentModel::class, SubjectModel::class, StudentSubjectCrossRef::class], version = 2)
abstract class StudentDatabase : RoomDatabase() {

    //abstração da interface, temos o acesso via esta instância do banco
    abstract fun studentDAO(): StudentDAO
    abstract fun subjectDAO(): SubjectDAO

    //PADRÃO SINGLETON (impedimos instanciar mais de um banco)
    companion object {
        private lateinit var INSTANCE: StudentDatabase

        fun getDatabase(context: Context): StudentDatabase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE =
                        Room.databaseBuilder(context, StudentDatabase::class.java, "bluePresenceDB")
                            .addMigrations(MIGRATION_1_2)
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //IMPLEMENTAR O NECESSÁRIO
            }

        }

    }
}