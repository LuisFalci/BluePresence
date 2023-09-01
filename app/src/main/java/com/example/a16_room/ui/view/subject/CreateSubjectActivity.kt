package com.example.a16_room.ui.view.subject

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.R
import com.example.a16_room.databinding.ActivityCreateSubjectBinding
import com.example.a16_room.ui.viewmodels.ScheduleViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel
import java.util.Calendar

class CreateSubjectActivity : AppCompatActivity() {
    private lateinit var subjectVewModel: SubjectViewModel
    private lateinit var scheduleViewModel: ScheduleViewModel

    private lateinit var selectedButton: Button

    lateinit var binding: ActivityCreateSubjectBinding

    data class ClassTime(
        val dayOfWeek: String,
        var startTime: String = "",
        var endTime: String = ""
    )

    private val classTimesList = mutableListOf<ClassTime>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.mondayStart.setOnClickListener {
            selectedButton = binding.mondayStart
            showTimePicker(selectedButton, "Segunda")
        }
        binding.mondayEnd.setOnClickListener {
            selectedButton = binding.mondayEnd
            showTimePicker(selectedButton, "Segunda")
        }
        binding.tuesdayStart.setOnClickListener {
            selectedButton = binding.tuesdayStart
            showTimePicker(selectedButton, "Terca")
        }
        binding.tuesdayEnd.setOnClickListener {
            selectedButton = binding.tuesdayEnd
            showTimePicker(selectedButton, "Terca")
        }
        binding.wednesdayStart.setOnClickListener {
            selectedButton = binding.wednesdayStart
            showTimePicker(selectedButton, "Quarta")
        }
        binding.wednesdayEnd.setOnClickListener {
            selectedButton = binding.wednesdayEnd
            showTimePicker(selectedButton, "Quarta")
        }
        binding.thursdayStart.setOnClickListener {
            selectedButton = binding.thursdayStart
            showTimePicker(selectedButton, "Quinta")
        }
        binding.thursdayEnd.setOnClickListener {
            selectedButton = binding.thursdayEnd
            showTimePicker(selectedButton, "Quinta")
        }
        binding.fridayStart.setOnClickListener {
            selectedButton = binding.fridayStart
            showTimePicker(selectedButton, "Sexta")
        }
        binding.fridayEnd.setOnClickListener {
            selectedButton = binding.fridayEnd
            showTimePicker(selectedButton, "Sexta")
        }

        subjectVewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        scheduleViewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]

        binding.buttonInsert.setOnClickListener {
            val name = binding.editName.text.toString()

            // Insira a matéria e obtenha o ID
            val subjectId = subjectVewModel.insert(name)

            // Agora, insira os horários na tabela Schedules
            for (classTime in classTimesList) {
                val startTime = classTime.startTime
                val endTime = classTime.endTime
                val dayOfWeek = classTime.dayOfWeek

                scheduleViewModel.insert(subjectId, startTime, endTime, dayOfWeek)
            }

            finish()
        }


    }

    private fun showTimePicker(button: Button, day: String) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                button.text = formattedTime

                // Encontre o objeto ClassTime correspondente e atualize o horário
                val classTime = classTimesList.find { it.dayOfWeek == day }
                classTime?.let {
                    if (button.id == R.id.mondayStart) {
                        it.startTime = formattedTime
                    } else if (button.id == R.id.mondayEnd) {
                        it.endTime = formattedTime
                    }
                    // Continue esse padrão para os outros dias da semana
                }
            },
            currentHour,
            currentMinute,
            true
        )
        timePickerDialog.show()
    }

}