package com.example.a16_room.ui.view.subject

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.databinding.ActivityCreateSubjectBinding
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class CreateSubjectActivity : AppCompatActivity() {
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var selectedButton: Button

    lateinit var binding: ActivityCreateSubjectBinding

    data class ClassTime(
        val dayOfWeek: String = "",
        var startTime: String = "",
        var endTime: String = "",
    )

    private val classTimesList = mutableListOf<ClassTime>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Criar Turma"

        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.buttonInsert.setOnClickListener {
            if (!validateForm()) {
                val name = binding.editName.text.toString()
                subjectViewModel.insert(name)

                finish()
            }
        }
    }

    private fun validateForm(): Boolean {
        var error = false
        Log.d("fkdlsjfsdkjfsld", "${binding.editName.text.length}")
        if (binding.editName.text.isEmpty()) {
            binding.editName.error = "Campo nome precisa ser preenchido"
            error = true
        }
        if (binding.editName.text.length >= 20) {
            Log.d("fkdlsjfsdkjfsld", "${binding.editName.text.length}")
            binding.editName.error = "Campo nome deve ter menos de 20 caracteres"
            error = true
        }
        return error
    }
}