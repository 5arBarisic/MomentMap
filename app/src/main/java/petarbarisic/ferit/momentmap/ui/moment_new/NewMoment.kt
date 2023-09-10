package petarbarisic.ferit.momentmap.ui.moment_new

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import petarbarisic.ferit.momentmap.R
import petarbarisic.ferit.momentmap.databinding.FragmentNewMomentBinding
import petarbarisic.ferit.momentmap.di.MomentRepositoryFactory
import petarbarisic.ferit.momentmap.model.Moment
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class NewMoment : Fragment() {

    private val momentRepository = MomentRepositoryFactory.momentRepository
    lateinit var binding: FragmentNewMomentBinding

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this.context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this.context,
            R.anim.rotate_close_anim
        )
    }
    private val fromBot: Animation by lazy {
        AnimationUtils.loadAnimation(
            this.context,
            R.anim.from_bottom_anim
        )
    }
    private val toBot: Animation by lazy {
        AnimationUtils.loadAnimation(
            this.context,
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMomentBinding.inflate(layoutInflater)
        binding.btnFinish.setOnClickListener { saveMoment() }
        binding.btnExit.setOnClickListener { quitMoment() }
        binding.etDate.setOnClickListener {onDateClick()}
        binding.btnAdd.setOnClickListener { onAddClick() }
        return binding.root
    }

    private fun onAddClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked

    }
    private fun onDateClick(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this.requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                var m = (monthOfYear + 1).toString()
                var d = dayOfMonth.toString()
                if ((monthOfYear + 1) < 10) {
                    m = "0$m"
                }
                if (dayOfMonth < 10) {
                    d = "0$d"
                }
                val dat = ("$d/$m/$year")
                binding.etDate.setText(dat)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.btnAddLocation.visibility = View.VISIBLE
            binding.btnAddPhoto.visibility = View.VISIBLE

        } else {
            binding.btnAddLocation.visibility = View.INVISIBLE
            binding.btnAddPhoto.visibility = View.INVISIBLE
        }

    }

    private fun setAnimation(clicked: Boolean) {

        if (!clicked) {
            binding.btnAddLocation.startAnimation(fromBot)
            binding.btnAddPhoto.startAnimation(fromBot)
            binding.btnAdd.startAnimation(rotateOpen)

        } else {
            binding.btnAddLocation.startAnimation(toBot)
            binding.btnAddPhoto.startAnimation(toBot)
            binding.btnAdd.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.btnAddLocation.isClickable = true
            binding.btnAddPhoto.isClickable = true


        } else {
            binding.btnAddLocation.isClickable = false
            binding.btnAddPhoto.isClickable = false
        }
    }


    private fun saveMoment() {

        val title = binding.etNaslov.text.toString()
        val contents = binding.etUnos.text.toString()
        val date = binding.etDate.text.toString()


        momentRepository.save(
            Moment(
                0,
                title,
                contents,
                date
            )
        )
        val action = NewMomentDirections.actionNewMomentToMainPageFragment()
        findNavController().navigate(action)
    }

    private fun quitMoment() {
        val action = NewMomentDirections.actionNewMomentToMainPageFragment()
        findNavController().navigate(action)

    }


    companion object {
        val Tag = "NewMoment"

        fun create(): Fragment {
            return NewMoment()
        }
    }
}