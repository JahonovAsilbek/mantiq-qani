package uz.revolution.mantiq_qani.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.revolution.mantiq_qani.R
import uz.revolution.mantiq_qani.databinding.MenuFragmentBinding

class MenuFragment : Fragment() {
    lateinit var binding: MenuFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuFragmentBinding.inflate(layoutInflater, container, false)

        binding.scores.setOnClickListener {
            findNavController().navigate(R.id.scoresFragment)
        }
        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
        binding.about.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }
        binding.cancel.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }
}