package com.example.a16_room.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.databinding.ActivityAttendanceBinding
import com.example.a16_room.ui.adapters.AttendanceAdapter
import com.example.a16_room.ui.listeners.OnAttendanceListener
import com.example.a16_room.ui.viewmodels.AttendanceViewModel
import com.example.a16_room.ui.viewmodels.StudentAttendanceViewModel
import com.example.a16_room.ui.viewmodels.StudentViewModel

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var attendanceAdapter: AttendanceAdapter
    private lateinit var attendanceViewModel: AttendanceViewModel
    private lateinit var studentAttendanceViewModel: StudentAttendanceViewModel

    private val studentList: MutableList<StudentModel> = mutableListOf()

    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager

    val REQUEST_ACCESS_COARSE_LOCATION = 101

    private var bluetoothDevicesFound = mutableListOf<String>()
    private val bluetoothDevicesFoundLiveData = MutableLiveData<List<String>>()

    private var subjectId: Long = -1L

    private var studentAttendanceMap = mutableMapOf<Long, Boolean>()
    private var studentWithDeviceMap = mutableMapOf<Long, String>()

    private var isButtonClicked = false

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Chamada"

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }

        studentViewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]
        studentAttendanceViewModel = ViewModelProvider(this)[StudentAttendanceViewModel::class.java]

        bluetoothDevicesFoundLiveData.observe(this) { updatedBluetoothDevicesFound ->
            bluetoothDevicesFound = updatedBluetoothDevicesFound.toMutableList()
            attendanceAdapter.updateBluetoothDevicesFound(bluetoothDevicesFound)
        }

        studentViewModel.getAllStudentsInSubject(subjectId)
        studentViewModel.students.observe(this) { students ->
            studentList.clear()
            studentList.addAll(students)
            studentAttendanceMap.clear()
            studentList.forEach { student ->
                studentAttendanceMap[student.studentId] = false
                studentWithDeviceMap[student.studentId] = student.macAddress
            }
            binding.totalStudents.text = "Total de alunos: " + studentList.size.toString()
            attendanceAdapter.notifyDataSetChanged()
        }

        val recyclerViewStudent = binding.studentsAttendance
        recyclerViewStudent.layoutManager = LinearLayoutManager(this)
        attendanceAdapter = AttendanceAdapter(this, studentList, bluetoothDevicesFound)
        recyclerViewStudent.adapter = attendanceAdapter
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        binding.startBluetoothAttendance.setOnClickListener {
            enableDisableBluetooth()
            if (isButtonClicked) {
                binding.startBluetoothAttendance.setBackgroundColor(Color.rgb(80, 163, 237))
                binding.startBluetoothAttendance.text = "Ativar Chamada"
                isButtonClicked = false
            } else {
                binding.startBluetoothAttendance.setBackgroundColor(Color.rgb(239, 27, 44))
                binding.startBluetoothAttendance.text = "Desativar Chamada"
                isButtonClicked = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when (ContextCompat.checkSelfPermission(
                        baseContext, Manifest.permission.ACCESS_COARSE_LOCATION
                    )) {
                        PackageManager.PERMISSION_DENIED -> AlertDialog.Builder(
                            this
                        )
                            .setTitle("Runtime Permission")
                            .setMessage("Give Permission")
                            .setNeutralButton(
                                "Okay",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (ContextCompat.checkSelfPermission(
                                            baseContext,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        ) !=
                                        PackageManager.PERMISSION_GRANTED
                                    ) {
                                        ActivityCompat.requestPermissions(
                                            this,
                                            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                                            REQUEST_ACCESS_COARSE_LOCATION
                                        )
                                    }
                                })
                            .show()
                            .findViewById<TextView>(androidx.appcompat.R.id.message)!!.movementMethod =
                            LinkMovementMethod.getInstance()
                    }
                }
                discoverDevices()
            }
            checkPermissions()
        }



        binding.seveAttendance.setOnClickListener {
            if (!studentAttendanceMap.isEmpty()) {
                var dateTime: Long = System.currentTimeMillis()
                val totalStudents = studentAttendanceMap.size
                val totalPresents = calculateTotalStudentsPresent()
                val attendanceId =
                    attendanceViewModel.insert(subjectId, dateTime, totalStudents, totalPresents)
                for ((studentId, isPresent) in studentAttendanceMap) {
                    if(bluetoothDevicesFound.contains(studentWithDeviceMap[studentId])){
                        Log.d("presentedfshjfdhskfjd", "${studentWithDeviceMap[studentId]}")
                        studentAttendanceViewModel.insert(attendanceId, subjectId, studentId, true)
                    }else{
                        studentAttendanceViewModel.insert(attendanceId, subjectId, studentId, isPresent)
                    }
                }
                finish()
            }
        }

        val listener = object : OnAttendanceListener {
            override fun onStudentClick(studentId: Long, isPresent: Boolean) {
                studentAttendanceMap[studentId] = isPresent
                Log.d("presente", "${studentAttendanceMap}")
            }
        }
        attendanceAdapter.attachListener(listener)
    }

    private fun calculateTotalStudentsPresent(): Int {
        var numeroAlunosPresentes = 0
        for (isPresent in studentAttendanceMap.values) {
            if (isPresent) {
                numeroAlunosPresentes++
            }
        }
        return numeroAlunosPresentes
    }


    @SuppressLint("MissingPermission")
    private fun discoverDevices() {
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        registerReceiver(discoverDeviceReceiver, filter)
        bluetoothAdapter.startDiscovery()
    }

    private val discoverDeviceReceiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            var action = ""
            if (intent != null) {
                action = intent.action.toString()
            }
            when (action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    Log.d("discoverDevices", "STATE CHANGED")
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d("discoverDevices", "Discovery Started")
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("discoverDevices", "${isButtonClicked}")
                    if (isButtonClicked === true) {
                        discoverDevices()
                    }
                }

                BluetoothDevice.ACTION_FOUND -> {
                    val device =
                        intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        Log.d("discoverDevices4", "${device.name}  ${device.address}")
                        bluetoothDevicesFound.add(device.address)
                        bluetoothDevicesFoundLiveData.value = bluetoothDevicesFound
                    }
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun enableDisableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            btActivityResultLauncher.launch(enableBtIntent)
            isButtonClicked = true
            Log.d("discoverDevices", "enableDisableBluetooth ${isButtonClicked}")
        }

    }

    private val btActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            Toast.makeText(this, "Bluetooth Conectado", Toast.LENGTH_LONG).show()
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

    override fun onDestroy() {
        super.onDestroy()
    }
}