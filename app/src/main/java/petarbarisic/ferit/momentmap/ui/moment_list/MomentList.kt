package petarbarisic.ferit.momentmap.ui.moment_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import petarbarisic.ferit.momentmap.databinding.FragmentMomentListBinding
import petarbarisic.ferit.momentmap.di.MomentRepositoryFactory
import petarbarisic.ferit.momentmap.model.Moment


class MomentList : Fragment(), OnMomentSelectedListener {

    private lateinit var binding: FragmentMomentListBinding
    private lateinit var adapter: MomentAdapter
    private lateinit var moments: ArrayList<Moment>
    private val momentRepository = MomentRepositoryFactory.momentRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMomentListBinding.inflate(layoutInflater)
        setupRecyclerView()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        updateData()
    }


    private fun updateData() {
        adapter.setMoments(momentRepository.getAllMoments())
    }


    private fun setupRecyclerView() {
        binding.momentListRvMoments.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = MomentAdapter(moments)
        adapter.onMomentSelectedListener = this
        binding.momentListRvMoments.adapter = adapter
    }


    companion object {
        val Tag = "MomentDetails"

        fun create(): Fragment {
            return MomentList()
        }
    }

    override fun onMomentSelected(date: String) {

       val action =
            MomentListDirections.actionMomentListToMomentDetails("09/02/2023")
        findNavController().navigate(action)
    }
}