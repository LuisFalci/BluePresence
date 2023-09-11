package com.example.a16_room.ui.view.subject

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.data.models.ScheduleModel
import com.example.a16_room.databinding.ActivityEditSubjectBinding
import com.example.a16_room.ui.viewmodels.ScheduleViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel
import java.util.Calendar

class EditSubjectActivity : AppCompatActivity() {
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var scheduleViewModel: ScheduleViewModel

    private lateinit var selectedButton: Button

    private lateinit var binding: ActivityEditSubjectBinding
    private var subjectId: Long = -1L

    data class ClassTime(
        val dayOfWeek: String = "",
        var startTime: String = "",
        var endTime: String = "",
    )

    private lateinit var scheduleDataList: List<ScheduleModel>
    private val classTimesList = mutableListOf<CreateSubjectActivity.ClassTime>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Editar Turma"

        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        scheduleViewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)


        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }
        if (subjectId > 0) {
            subjectViewModel.get(subjectId)
            subjectViewModel.subject.observe(this) { subject ->
                subjectId = subject.subjectId
                binding.editName.setText(subject.subjectName)
            }
            scheduleDataList = scheduleViewModel.getAllSchedulesForSubject(subjectId)
            loadFields(scheduleDataList)
        }

        // Auto completa os campos que possuem dados no banco


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

        binding.buttonEdit.setOnClickListener {
            val name = binding.editName.text.toString()
            subjectViewModel.update(subjectId, name)

            for (classTime in classTimesList) {
                var startTime = classTime.startTime
                var endTime = classTime.endTime
                var dayOfWeek = classTime.dayOfWeek

                val scheduleItem = scheduleDataList.find { it.dayOfWeek == dayOfWeek }
                if (scheduleItem != null) {
                    val scheduleId = scheduleItem.scheduleId
                    if(startTime.isEmpty()){
                        startTime = scheduleItem.startTime
                    }
                    if(endTime.isEmpty()){
                        endTime = scheduleItem.endTime
                    }
                    scheduleViewModel.update(scheduleId, subjectId, startTime, endTime, dayOfWeek)
                }
                else{
                    scheduleViewModel.insert(subjectId, startTime, endTime, dayOfWeek)
                }
            }
            finish()
        }
    }

    private fun showTimePicker(button: Button, day: String) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Verifique se a lista contém o dia, se não, adicione-o
        val classTime = classTimesList.find { it.dayOfWeek == day }
        if (classTime == null) {
            classTimesList.add(CreateSubjectActivity.ClassTime(day))
        }

        val buttonName = resources.getResourceEntryName(button.id)
        val isStartButton = buttonName.contains("Start")

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                button.text = formattedTime

                // Atualize o horário no objeto ClassTime correspondente
                val updatedClassTime = classTimesList.find { it.dayOfWeek == day }
                updatedClassTime?.let {
                    if (isStartButton) {
                        it.startTime = formattedTime
                    }
                    if (!isStartButton) {
                        it.endTime = formattedTime
                    }
                }
            },
            currentHour,
            currentMinute,
            true
        )
        timePickerDialog.show()
    }

    private fun loadFields(scheduleDataList: List<ScheduleModel>) {

        for (scheduleData in scheduleDataList) {
            val dayOfWeek = scheduleData.dayOfWeek
            val startTime = scheduleData.startTime
            val endTime = scheduleData.endTime

            when (dayOfWeek) {
                "Segunda" -> {
                    binding.mondayStart.text = startTime
                    binding.mondayEnd.text = endTime
                }
                "Terca" -> {
                    binding.tuesdayStart.text = startTime
                    binding.tuesdayEnd.text = endTime
                }
                "Quarta" -> {
                    binding.wednesdayStart.text = startTime
                    binding.wednesdayEnd.text = endTime
                }
                "Quinta" -> {
                    binding.thursdayStart.text = startTime
                    binding.thursdayEnd.text = endTime
                }
                "Sexta" -> {
                    binding.fridayStart.text = startTime
                    binding.fridayEnd.text = endTime
                }
                // Se você tem outros dias da semana, você pode adicionar mais casos aqui
                else -> {
                    // Lógica para outros dias da semana, se necessário
                }
            }
        }
    }

}