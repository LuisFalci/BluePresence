package com.example.a16_room.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.data.models.AttendanceModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()  {
    private var attendanceList: List<AttendanceModel> = listOf()
//    private lateinit var listener: OnSubjectListener
    var count = 0
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var historyText: TextView = view.findViewById(R.id.historyText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val history = LayoutInflater.from(parent.context).inflate(R.layout.row_history, parent, false)
        return ViewHolder(history)
    }

    override fun getItemCount(): Int {
        return attendanceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = attendanceList[position]
        count++
        holder.historyText.text = history.presence.toString()
        Log.d("fdjsflksjdfls", history.presence.toString())
//        holder.historyText.setOnClickListener {
//            listener.onDeviceClick(device.split("->")[1])
//        }
    }
    fun updateHistory(list: List<AttendanceModel>) {
        attendanceList = list
        notifyDataSetChanged()
    }

}