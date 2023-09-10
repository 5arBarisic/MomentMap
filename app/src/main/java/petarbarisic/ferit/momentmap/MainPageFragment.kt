package petarbarisic.ferit.momentmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import petarbarisic.ferit.momentmap.databinding.FragmentMainPageBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainPageFragment : Fragment() {


    private lateinit var binding: FragmentMainPageBinding

    private val firestore = FirebaseFirestore.getInstance();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainPageBinding.inflate(layoutInflater)
        binding.btnAddInput.setOnClickListener {
            firestore.collection("moments")
                .get()
                .addOnCompleteListener{
                    val title: StringBuffer = StringBuffer();
                    if(it.isSuccessful){
                        for(doc in it.result!!)
                            title.append(doc.data.getValue("title"))

                    }
                    binding.tvMainPageTitle.text = title;
                }
        }//showCreateNewTaskFragment() }
        return binding.root
    }


    private fun showCreateNewTaskFragment() {
        val action = MainPageFragmentDirections.actionMainPageFragmentToNewMoment()
        findNavController().navigate(action)
    }

}