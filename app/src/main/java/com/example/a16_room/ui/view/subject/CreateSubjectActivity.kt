package com.example.a16_room.ui.view.subject

import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.databinding.ActivityCreateSubjectBinding
import com.example.a16_room.ui.adapters.ScheduleAdapter
import com.example.a16_room.ui.viewmodels.ScheduleViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class CreateSubjectActivity : AppCompatActivity() {
    private lateinit var subjectVewModel: SubjectViewModel
    private lateinit var scheduleViewModel: ScheduleViewModel

    lateinit var binding: ActivityCreateSubjectBinding
    private val selectedOptions = mutableListOf<String>()

    private lateinit var dayRecyclerView: RecyclerView
    private lateinit var scheduleAdapter: ScheduleAdapter


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

        dayRecyclerView = findViewById(R.id.dayRecyclerView)
        scheduleAdapter = ScheduleAdapter(this, selectedOptions)
        dayRecyclerView.adapter = scheduleAdapter
        dayRecyclerView.layoutManager = LinearLayoutManager(this)

        subjectVewModel = ViewModelProvider(this)[SubjectViewModel::class.java]
        scheduleViewModel =  ViewModelProvider(this)[ScheduleViewModel::class.java]

        binding.buttonInsert.setOnClickListener {
            val name = binding.editName.text.toString()

            subjectVewModel.insert(name)
//            scheduleViewModel.insert(startTime,  endTime, dayOfWeek)

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
                scheduleAdapter.notifyDataSetChanged()
            }
        }

    }
}