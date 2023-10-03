package com.example.a16_room.ui.view.subject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.databinding.ActivityViewSubjectBinding
import com.example.a16_room.ui.adapters.SubjectAdapter
import com.example.a16_room.ui.listeners.ClickSourceSubject
import com.example.a16_room.ui.listeners.OnSubjectListener
import com.example.a16_room.ui.view.SubjectMenuActivity
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class ViewSubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewSubjectBinding
    private lateinit var viewModel: SubjectViewModel
    private val adapter = SubjectAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Turmas"

        viewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.recyclerSubjects.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerSubjects.adapter = adapter

        val listener = object : OnSubjectListener {
            override fun OnClick(subjectId: Long, source: ClickSourceSubject) {
                when (source) {
                    ClickSourceSubject.OPTION_VIEW_STUDENTS -> {
//                        val intent = Intent(this@ViewSubjectActivity, ViewStudentActivity::class.java)
                        val intent =
                            Intent(this@ViewSubjectActivity, SubjectMenuActivity::class.java)
                        intent.putExtra("subject_id", subjectId)
                        startActivity(intent)
                    }

                    ClickSourceSubject.OPTION_EDIT -> {
                        val intent =
                            Intent(this@ViewSubjectActivity, EditSubjectActivity::class.java)
                        intent.putExtra("subject_id", subjectId)
                        startActivity(intent)
                    }

                    ClickSourceSubject.OPTION_REMOVE -> {
                        // Handle delete action directly here
                        viewModel.delete(subjectId)
                    }
                }
            }
        }

        binding.buttonNewSubject.setOnClickListener {
            startActivity(Intent(this, CreateSubjectActivity::class.java))
        }

        adapter.attachListener(listener)

        viewModel.getAll()
        observe()

        checkPermissions()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    private fun observe() {
        viewModel.subjects.observe(this) {
            adapter.updateSubjects(it)
        }
        viewModel.newChange.observe(this) {
            viewModel.getAll()
        }
    }

    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_PRIVILEGED
    )
    private val PERMISSIONS_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT,
        Manifest.permission.BLUETOOTH_PRIVILEGED
    )
    private fun checkPermissions() {
        val permission1 = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val permission2 = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_SCAN
        )
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1)
        } else if (permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_LOCATION, 1)
        }
    }
}