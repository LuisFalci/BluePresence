package com.example.a16_room.ui.view.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a16_room.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private var subjectId: Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var action = supportActionBar
        action!!.title = "Histórico de Chamadas"

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }
    }
}