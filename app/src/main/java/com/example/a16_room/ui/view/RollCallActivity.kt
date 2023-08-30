package com.example.a16_room.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a16_room.R
import com.example.a16_room.databinding.ActivityRollCallBinding

class RollCallActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRollCallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRollCallBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}