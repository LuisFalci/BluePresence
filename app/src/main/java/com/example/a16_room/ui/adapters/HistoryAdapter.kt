package com.example.a16_room.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.ui.listeners.OnHistoryListener
import java.text.SimpleDateFormat
import java.util.Date

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()  {
    private var attendanceList: List<AttendanceModel> = listOf()
    private lateinit var listener: OnHistoryListener
    var count = 0
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var historyText: TextView = view.findViewById(R.id.historyText)
        var attendancePercentage: TextView = view.findViewById(R.id.attendancePercentage)
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
        val date = Date(history.dateTime)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = sdf.format(date)
        holder.historyText.text = formattedDate
//        holder.attendancePercentage.text = "COLOCAR O PERCENTUAL DE PRESENTES"
        holder.historyText.setOnClickListener {
            listener.onHistoryClick(history.attendanceId)
        }
    }
    fun updateHistory(list: List<AttendanceModel>) {
        attendanceList = list
        notifyDataSetChanged()
    }
    fun attachListener(historyListener: OnHistoryListener) {
        listener = historyListener
    }
}