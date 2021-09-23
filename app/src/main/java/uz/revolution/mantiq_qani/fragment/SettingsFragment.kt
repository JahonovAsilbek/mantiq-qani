package uz.revolution.mantiq_qani.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.revolution.mantiq_qani.R
import uz.revolution.mantiq_qani.dataBase.MySharedPreferences
import uz.revolution.mantiq_qani.dialogs.DeleteDialog

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(container!!.context)
            .inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deleteBtn: ImageButton = view.findViewById(R.id.delete_scores_btn)
        val cancel: Button = view.findViewById(R.id.settings_cancel_btn)
        deleteBtn.setOnClickListener {
            val dialog = DeleteDialog(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dialog.setOnPositiveClickListener(object : DeleteDialog.OnPositiveClickListener {
                    override fun onClick(dialog: DeleteDialog) {
                        MySharedPreferences.deleteAllData()
                        dialog.cancel()
                    }
                })
                    .build()
            }
        }
        cancel.setOnClickListener { findNavController().popBackStack() }
    }
}