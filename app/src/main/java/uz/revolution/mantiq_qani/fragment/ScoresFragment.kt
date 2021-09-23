package uz.revolution.mantiq_qani.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.revolution.mantiq_qani.adapter.FragmentAdapter
import uz.revolution.mantiq_qani.dataBase.MySharedPreferences
import uz.revolution.mantiq_qani.databinding.ListFragmentBinding
import uz.revolution.mantiq_qani.model.MyData
import java.util.*

class ScoresFragment : Fragment() {
    private var data: ArrayList<MyData>? = null
    private var adapter: FragmentAdapter? = null
    lateinit var binding: ListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(layoutInflater, container, false)

        val database = MySharedPreferences.getUsersDataList()
        adapter = database.let { FragmentAdapter(it) }
        binding.listview.adapter = adapter

        binding.scoresCancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}