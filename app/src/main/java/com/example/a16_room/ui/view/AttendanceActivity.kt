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
import com.example.a16_room.ui.viewmodels.StudentViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var attendanceAdapter: AttendanceAdapter
    private lateinit var attendanceViewModel: AttendanceViewModel
    private val studentList: MutableList<StudentModel> = mutableListOf()

    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager

    val REQUEST_ACCESS_COARSE_LOCATION = 101

    private var bluetoothDevicesFound = mutableListOf<String>()
    private val bluetoothDevicesFoundLiveData = MutableLiveData<List<String>>()

    private var subjectId: Long = -1L

    private val studentAttendanceMap = mutableMapOf<Long, Boolean>()

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

        studentViewModel.getAllStudentsInSubject(subjectId)

        studentViewModel.students.observe(this) { students ->
            studentList.clear()
            studentList.addAll(students)
            binding.totalStudents.text = "Total de alunos: " + studentList.size.toString()
            attendanceAdapter.notifyDataSetChanged()
        }

        bluetoothDevicesFoundLiveData.observe(this) { updatedBluetoothDevicesFound ->
            bluetoothDevicesFound = updatedBluetoothDevicesFound.toMutableList()
            attendanceAdapter.updateBluetoothDevicesFound(bluetoothDevicesFound)
        }


        val recyclerViewStudent = binding.studentsAttendance
        recyclerViewStudent.layoutManager = LinearLayoutManager(this)
        attendanceAdapter = AttendanceAdapter(this, studentList, bluetoothDevicesFound)
        recyclerViewStudent.adapter = attendanceAdapter
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        binding.startBluetoothAttendance.setOnClickListener {
//            studentViewModel.insert("Luis", "1", "1")
//            studentViewModel.insert("Adailton", "2", "2")
//            studentViewModel.insert("Hammer", "3", "3")
//            studentViewModel.insert("Bleisson", "4", "4")
//            studentViewModel.insert("Jamer", "5", "5")
//            studentViewModel.insert("Kleiton", "6", "6")
//            studentViewModel.insert("Luis", "7", "7")
//            studentViewModel.insert("Bob", "12", "12")
//            studentViewModel.insert("Kreb", "120", "12")
//            studentViewModel.insert("Glub", "120", "120")
//            studentViewModel.insert("Brawle", "120", "2C:15:BF:8F:7D:BC")
//            studentViewModel.delete(1)
            enableDisableBluetooth()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                when (ContextCompat.checkSelfPermission(
                    baseContext, Manifest.permission.ACCESS_COARSE_LOCATION
                )) {
                    PackageManager.PERMISSION_DENIED -> AlertDialog.Builder(
                        this
                    )
                        .setTitle("Runtime Permission")
                        .setMessage("Give Permission")
                        .setNeutralButton("Okay", DialogInterface.OnClickListener { dialog, which ->
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
        binding.seveAttendance.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val current = LocalDateTime.now().format(formatter)

            for ((studentId, isPresent) in studentAttendanceMap) {
                attendanceViewModel.insert(studentId, isPresent, current)
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
                    Log.d("discoverDevices1", "STATE CHANGED")
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d("discoverDevices2", "Discovery Started")
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("discoverDevices3", "Disvcoery Fininshed")
                    discoverDevices()
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
        checkPermissions()
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            btActivityResultLauncher.launch(enableBtIntent)
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