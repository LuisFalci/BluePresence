package com.example.a16_room.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a16_room.databinding.ActivityAttendanceBinding

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}