package petarbarisic.ferit.momentmap.ui.diary

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateLongClickListener
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import org.threeten.bp.LocalDate
import petarbarisic.ferit.momentmap.databinding.FragmentDiaryBinding
import petarbarisic.ferit.momentmap.di.MomentRepositoryFactory
import org.threeten.bp.format.DateTimeFormatter;
import petarbarisic.ferit.momentmap.model.Moment
import petarbarisic.ferit.momentmap.ui.moment_list.MomentAdapter
import petarbarisic.ferit.momentmap.ui.moment_list.OnMomentSelectedListener

@RequiresApi(Build.VERSION_CODES.O)
class Diary : Fragment(), OnDateSelectedListener, OnDateLongClickListener,
    OnMomentSelectedListener {

    private lateinit var moments: ArrayList<Moment>
    private lateinit var adapter: MomentAdapter
    private lateinit var binding: FragmentDiaryBinding
    private val momentRepository = MomentRepositoryFactory.momentRepository
    val formatterWithoutTime = DateTimeFormatter.ofPattern("dd/MM/uuuu")
    var dateToShow: LocalDate = LocalDate.now()
    private var dbref = FirebaseFirestore.getInstance().collection("moments");


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDiaryBinding.inflate(layoutInflater)
        val widget = binding.calendarView
        widget.setOnDateChangedListener(this)
        widget.setOnDateLongClickListener(this)
        widget.setDateSelected(CalendarDay.today(), true)
        moments = arrayListOf()

        dbref.get()
            .addOnSuccessListener {
                if (!it.isEmpty)
                    for (data in it.documents) {
                        val moment: Moment? = data.toObject(Moment::class.java)
                        if (moment != null) {
                            moments.add(moment)
                        }
                    }

            }
        setupRecyclerView(moments)
        return binding.root
    }


    private fun updateData() {
        moments = arrayListOf()

        dbref.get()
            .addOnSuccessListener {
                if (!it.isEmpty)
                    for (data in it.documents) {
                        val moment: Moment? = data.toObject(Moment::class.java)
                        if (moment != null) {
                            moments.add(moment)
                        }
                    }

            }
        adapter.setMoments(moments);
       /* adapter.setMoments(
            momentRepository.getAllMomentsByDate(
                dateToShow.format(
                    formatterWithoutTime
                )
            )
        )*/
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun deleteData() {
        momentRepository.deleteAllMoments()
    }


    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        if (selected) {

            deleteData()
            dateToShow = date.date
            updateData()

        }


    }

    companion object {
        val Tag = "Diary"

        fun create(): Fragment {
            return Diary()
        }
    }


    private fun setupRecyclerView(list:ArrayList<Moment>) {
        binding.diaryRvMoments.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = MomentAdapter(list)
        adapter.onMomentSelectedListener = this
        binding.diaryRvMoments.adapter = adapter
    }

    override fun onDateLongClick(widget: MaterialCalendarView, date: CalendarDay) {
        val requestDate = date.date.format(formatterWithoutTime)
        val action = DiaryDirections.actionDiaryToMomentDetails(requestDate)
        findNavController().navigate(action)
    }

    override fun onMomentSelected(date: String) {

        val action = DiaryDirections.actionDiaryToMomentDetails(date)
        findNavController().navigate(action)
    }
}


