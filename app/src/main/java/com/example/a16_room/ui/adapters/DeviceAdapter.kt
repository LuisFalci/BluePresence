package com.example.a16_room.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.ui.listeners.OnDeviceClickListener

class DeviceAdapter(private var devicesList: List<String>) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {
    private lateinit var listener: OnDeviceClickListener
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var deviceTextView: TextView = view.findViewById(R.id.device)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val device = LayoutInflater.from(parent.context).inflate(R.layout.row_device, parent, false)
        return ViewHolder(device)
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = devicesList[position]
        holder.deviceTextView.text = device
        holder.deviceTextView.setOnClickListener {
            listener.onDeviceClick(device.split("->")[1])
        }
    }
    fun attachListener(deviceListener: OnDeviceClickListener) {
        listener = deviceListener
    }
}