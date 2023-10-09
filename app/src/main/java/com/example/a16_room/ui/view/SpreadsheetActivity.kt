package com.example.a16_room.ui.view

import StudentInfoAdapter
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.data.models.utils.StudentAttendanceInfo
import com.example.a16_room.databinding.ActivitySpreadsheetBinding
import com.example.a16_room.ui.viewmodels.AttendanceViewModel
import com.example.a16_room.ui.viewmodels.StudentAttendanceViewModel
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class SpreadsheetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpreadsheetBinding
    private var subjectId: Long = -1L

    private lateinit var dateTimeArray: Array<String>
    private lateinit var studentNameArray: Array<String>
    private lateinit var attendanceViewModel: AttendanceViewModel
    private lateinit var studentAttendanceViewModel: StudentAttendanceViewModel

    private lateinit var studentInfoRecyclerView: StudentInfoAdapter

    private lateinit var studentInfoList: List<StudentAttendanceInfo>

    private val WRITE_EXTERNAL_STORAGE_REQUEST = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpreadsheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Relatório da Turma"

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }

        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]
        studentAttendanceViewModel = ViewModelProvider(this)[StudentAttendanceViewModel::class.java]


        studentAttendanceViewModel.getStudentAttendanceInfo(subjectId)

        studentAttendanceViewModel.studentAttendanceInfo.observe(this) { infoList ->
            studentInfoList = infoList.map { studentInfo ->
                StudentAttendanceInfo(
                    studentInfo.studentName,
                    studentInfo.totalAbsences,
                    studentInfo.totalPresences,
                    studentInfo.totalStudents,
                    studentInfo.attendancePercentage
                )
            }

            val recyclerViewStudent = binding.studentInfoRecyclerView
            recyclerViewStudent.layoutManager = LinearLayoutManager(this)
            studentInfoRecyclerView = StudentInfoAdapter(studentInfoList)
            recyclerViewStudent.adapter = studentInfoRecyclerView
        }

        attendanceViewModel.getAllAttendancesFromSubject(subjectId)
        val attendances = attendanceViewModel.attendances.value
        dateTimeArray = attendances?.map { it.dateTime.toString() }?.toTypedArray() ?: emptyArray()

        binding.createExcelFile.setOnClickListener {
            createExcelFile(studentInfoList)
        }
    }

    private fun createExcelFile(studentInfoList: List<StudentAttendanceInfo>) {
        val hssfWorkbook = HSSFWorkbook()
        val hssfSheet = hssfWorkbook.createSheet("MySheet")

        val headers = arrayOf("Nome", "Ausências", "Presenças", "Total", "Frequência")

        val headerRow: HSSFRow = hssfSheet.createRow(0)
        for (i in headers.indices) {
            val headerCell: HSSFCell = headerRow.createCell(i)
            headerCell.setCellValue(headers[i])
        }

        for ((index, studentInfo) in studentInfoList.withIndex()) {
            val dataRow: HSSFRow =
                hssfSheet.createRow(index + 1)

            val nameCell: HSSFCell = dataRow.createCell(0)
            nameCell.setCellValue(studentInfo.studentName)

            val absencesCell: HSSFCell = dataRow.createCell(1)
            absencesCell.setCellValue(studentInfo.totalAbsences.toDouble())

            val totalStudentsCell: HSSFCell = dataRow.createCell(2)
            totalStudentsCell.setCellValue(studentInfo.totalPresences.toDouble())

            val presencesCell: HSSFCell = dataRow.createCell(3)
            presencesCell.setCellValue(studentInfo.totalAbsences.toDouble() + studentInfo.totalPresences.toDouble())

            val formattedPercentage = String.format("%.2f", studentInfo.attendancePercentage)
            val attendancePercentageCell: HSSFCell = dataRow.createCell(4)
            attendancePercentageCell.setCellValue(formattedPercentage)
        }

        saveWorkBook(hssfWorkbook)
    }


    private fun saveWorkBook(hssfWorkbook: HSSFWorkbook) {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileOutput = File(directory, "Chamada.xls")

        try {
            val fileOutputStream = FileOutputStream(fileOutput)
            hssfWorkbook.write(fileOutputStream)
            fileOutputStream.close()
            hssfWorkbook.close()
            Toast.makeText(this, "Planilha criada com sucesso", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao criar Planilha, tente novamente", Toast.LENGTH_LONG)
                .show()
            throw RuntimeException(e)
        }
    }
}