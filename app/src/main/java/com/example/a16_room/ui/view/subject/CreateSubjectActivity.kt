package com.example.a16_room.ui.view.subject

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.R
import com.example.a16_room.databinding.ActivityCreateSubjectBinding
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class CreateSubjectActivity : AppCompatActivity() {
    private lateinit var viewModel: SubjectViewModel
    lateinit var binding: ActivityCreateSubjectBinding
    private val selectedOptions = mutableListOf<String>()
    private lateinit var dayListView: ListView
    private lateinit var selectedDaysArrayAdapter: ArrayAdapter<String>


    private val checkBoxes: List<CheckBox> by lazy {
        listOf(
                binding.fragmentCreatealarmCheckMon,
                binding.fragmentCreatealarmCheckTue,
                binding.fragmentCreatealarmCheckWed,
                binding.fragmentCreatealarmCheckThu,
                binding.fragmentCreatealarmCheckFri
        )
    }

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
        val options = listOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta")

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val day = options[index]
                if (isChecked) {
                    selectedOptions.add(day)
                } else {
                    selectedOptions.remove(day)
                }
                dayListView = findViewById(R.id.dayListView)
                selectedDaysArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectedOptions)
                dayListView.adapter = selectedDaysArrayAdapter
            }
        }


    }
}
