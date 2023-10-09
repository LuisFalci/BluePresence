import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.R
import com.example.a16_room.data.models.utils.StudentAttendanceInfo

class StudentInfoAdapter(private val studentInfoList: List<StudentAttendanceInfo>) :
    RecyclerView.Adapter<StudentInfoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.name)
        val textViewAbsences: TextView = itemView.findViewById(R.id.absences)
        val textViewPresences: TextView = itemView.findViewById(R.id.presences)
        val textViewPercentage: TextView = itemView.findViewById(R.id.percentage)
        val textViewTotalStudents: TextView = itemView.findViewById(R.id.totalStudents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_student_info, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studentInfo = studentInfoList[position]

        holder.textViewName.text = studentInfo.studentName
        holder.textViewAbsences.text = studentInfo.totalAbsences.toString()
        holder.textViewPresences.text = studentInfo.totalPresences.toString()
        holder.textViewTotalStudents.text = studentInfo.totalStudents.toString()
        val formattedPercentage = String.format("%.1f", studentInfo.attendancePercentage)
        holder.textViewPercentage.text = formattedPercentage + "%"
    }

    override fun getItemCount(): Int {
        return studentInfoList.size
    }
}
