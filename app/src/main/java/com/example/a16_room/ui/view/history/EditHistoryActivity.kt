package com.example.a16_room.ui.view.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a16_room.databinding.ActivityEditHistoryBinding

class EditHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}