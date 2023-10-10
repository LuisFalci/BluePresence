package com.example.a16_room.ui.view.student

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.databinding.ActivityCreateStudentBinding
import com.example.a16_room.ui.adapters.DeviceAdapter
import com.example.a16_room.ui.listeners.OnDeviceClickListener
import com.example.a16_room.ui.viewmodels.StudentViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class CreateStudentActivity : AppCompatActivity() {
    private lateinit var viewModel: StudentViewModel
    private lateinit var subjectViewModel: SubjectViewModel

    lateinit var binding: ActivityCreateStudentBinding
    private var subjectId: Long = -1L

    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager

    var permission: Boolean = false
    val REQUEST_ACCESS_COARSE_LOCATION = 101

    private lateinit var deviceAdapter: DeviceAdapter
    private val devicesList = ArrayList<String>()
    private val devicesNameList = ArrayList<String>()
    private val devicesAddressList = ArrayList<String>()
    private var pairedDevicesList = ArrayList<String>()
    private var macAddress: String = ""

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Criar Aluno"

        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }

        binding.buttonInsert.setOnClickListener {
            if (!validateForm()) {
                val name = binding.editName.text.toString()
                val registration = binding.editRegistration.text.toString()

                val insertedStudentId = viewModel.insert(name, registration, macAddress)

                if (insertedStudentId > 0 && subjectId != -1L) {
                    subjectViewModel.insertStudentSubject(insertedStudentId, subjectId)
                }
                finish()
            }
        }

        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        binding.btDiscoverDevices.setOnClickListener {
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
        val recyclerView: RecyclerView = binding.deviceRecycler
        deviceAdapter = DeviceAdapter(devicesList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = deviceAdapter

        val listener = object : OnDeviceClickListener {
            override fun onDeviceClick(address: String) {
                pairWithDevice(address)
            }
        }

        deviceAdapter.attachListener(listener)

        val bondStateFilter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        registerReceiver(bondStateChangedReceiver, bondStateFilter)

        checkPermissions()
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
                BluetoothDevice.ACTION_FOUND -> {
                    val device =
                        intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {

                        if (device.name != null && !devicesNameList.contains(device.name)) {
                            devicesNameList.add("${device.name}")
                            devicesList.add("${device.name}->${device.address}")
                        } else if (device.name == null && device.address != null && !devicesAddressList.contains(
                                device.address
                            )
                        ) {
                            devicesAddressList.add("${device.address}")
                            devicesList.add("${device.address}")
                        }
                        deviceAdapter.notifyDataSetChanged()
                    }
                }

                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    binding.btDiscoverDevices.isEnabled = false
                }

                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    binding.btDiscoverDevices.isEnabled = true
                }
            }
        }

    }

    private val bondStateChangedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED == action) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                val bondState =
                    intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR)

                when (bondState) {
                    BluetoothDevice.BOND_BONDED -> {
                        // O dispositivo foi pareado com sucesso
                        macAddress = device?.address.toString()
                        binding.deviceFound.text = "Pareado"
                        Log.d(
                            "BluetoothReceiver",
                            "Dispositivo pareado com sucesso: ${device?.name} - ${macAddress}"
                        )
                    }

                    BluetoothDevice.BOND_BONDING -> {
                        // O dispositivo está atualmente em processo de pareamento
                        Log.d(
                            "BluetoothReceiver",
                            "Pareando dispositivo: ${device?.name} - ${device?.address}"
                        )
                    }

                    BluetoothDevice.BOND_NONE -> {
                        // O dispositivo não está pareado
                        Log.d(
                            "BluetoothReceiver",
                            "Dispositivo não pareado: ${device?.name} - ${device?.address}"
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getPairedDevices() {
        var arr = bluetoothAdapter.bondedDevices
        Log.d("bondedDevices", arr.size.toString())
        Log.d("bondedDevices", arr.toString())
        pairedDevicesList = ArrayList(arr.map { it.name + " - " + it.address })
        Log.d("pairedDevicesList", pairedDevicesList.toString())
        for (device in arr) {
            Log.d("bondedDevices", device.name + "   " + device.address + "   " + device.bondState)
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun enableDisableBluetooth() {
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

    @SuppressLint("MissingPermission")
    private fun pairWithDevice(macAddress: String) {
        val remoteDevice = bluetoothAdapter.getRemoteDevice(macAddress)
        try {
            remoteDevice.createBond()
            Log.d("pairStatus", "pair succes")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("pairStatus", "pair failed")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bondStateChangedReceiver)
    }
}
