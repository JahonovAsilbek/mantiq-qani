package uz.revolution.mantiq_qani.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.revolution.mantiq_qani.R

class InfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(container!!.context)
            .inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cancel: Button = view.findViewById(R.id.info_cancel_btn)
        val info: TextView = view.findViewById(R.id.info_text)
        var infoText: String? = null
        if (arguments != null) {
            infoText = requireArguments().getString("comment")
        }
        if (infoText != null) {
            info.text = infoText
        } else {
            info.text = "no commentary"
        }
        cancel.setOnClickListener { findNavController().popBackStack() }
    }
}