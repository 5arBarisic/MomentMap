package petarbarisic.ferit.momentmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import petarbarisic.ferit.momentmap.databinding.FragmentMomentDetailsBinding
import petarbarisic.ferit.momentmap.di.MomentRepositoryFactory
import petarbarisic.ferit.momentmap.model.Moment

class MomentDetails : Fragment() {
    private lateinit var binding: FragmentMomentDetailsBinding
    private val taskRepository =MomentRepositoryFactory.momentRepository
    private val args: MomentDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMomentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task = taskRepository.getMomentByDate(args.momentDate)
        display(task)
    }

    private fun display(moment: Moment?) {
        moment?.let {
            binding.apply {
                tvMomentDetailsTitle.text = moment.title
                tvMomentDetailsContents.text = moment.content
                tvMomentDetailsDate.text = moment.date
            }
        }
    }

    companion object {
        val Tag = "MomentDetails"
        val momentDateKey = "momentDate"

        fun create(date: String): Fragment {
            return MomentDetails()
        }
    }
}