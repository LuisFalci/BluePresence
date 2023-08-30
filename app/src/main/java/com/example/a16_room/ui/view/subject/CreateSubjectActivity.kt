package com.example.a16_room.ui.view.subject

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.R
import com.example.a16_room.databinding.ActivityCreateSubjectBinding
import com.example.a16_room.ui.viewmodels.SubjectViewModel
import java.util.Calendar

class CreateSubjectActivity : AppCompatActivity() {
    private lateinit var viewModel: SubjectViewModel
    lateinit var binding: ActivityCreateSubjectBinding
    private lateinit var mainLayout: LinearLayout
    private lateinit var showOptionsButton: Button
    private lateinit var optionsSpinner: Spinner
    private val selectedOptions = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.buttonInsert.setOnClickListener {
            val name = binding.editName.text.toString()

            viewModel.insert(name)
            finish()
        }

        mainLayout = findViewById(R.id.main_layout)
        optionsSpinner = findViewById(R.id.options_spinner)

        val options = listOf("","Segunda", "Terça", "Quarta", "Quinta", "Sexta")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        optionsSpinner.adapter = adapter

        var isFirstItemSelected = true
        optionsSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                if (!isFirstItemSelected) {
                    val selectedOption = options[position]
                    if(selectedOptions.contains(selectedOption)){
                        return
                    }
                    selectedOptions.add(selectedOption)
                } else {
                    isFirstItemSelected = false // Altera para false após a primeira seleção
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedOptions.clear()
            }
        })

        binding.buttonPickStartTime.setOnClickListener {
            showTimePickerDialog(true)
        }

        binding.buttonPickEndTime.setOnClickListener {
            showTimePickerDialog(false)
        }

        binding.buttonInsertDay.setOnClickListener {
            val selectedOptionsText = selectedOptions.joinToString(", ")
            binding.textSelectedOptions .text = "Opções selecionadas: $selectedOptionsText"
            val startTimeText = binding.buttonPickStartTime.text.toString()
            val endTimeText = binding.buttonPickEndTime.text.toString()

            val selectedText = "Opções selecionadas: $selectedOptionsText\n" +
                    "Horário: $startTimeText até $endTimeText"

            binding.textSelectedOptionsAndTimes.text = selectedText
        }
    }
    private fun showTimePickerDialog(isStartTime: Boolean) {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    if (isStartTime) {
                        binding.buttonPickStartTime.setText(selectedTime)
                    } else {
                        binding.buttonPickEndTime.setText(selectedTime)
                    }
                },
                hour,
                minute,
                true
        )

        timePickerDialog.show()
    }
}