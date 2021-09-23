package uz.revolution.mantiq_qani.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import uz.revolution.mantiq_qani.databinding.DeleteDialogBinding

class DeleteDialog(context: Context?) : AlertDialog(context) {
    private var onPositiveClickListener: OnPositiveClickListener? = null
    var binding: DeleteDialogBinding = DeleteDialogBinding.inflate(layoutInflater)

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun build(): DeleteDialog {
        create()
        show()
        return this
    }

    interface OnPositiveClickListener {
        fun onClick(dialog: DeleteDialog)
    }

    fun setOnPositiveClickListener(onPositiveClickListener: OnPositiveClickListener): DeleteDialog {
        this.onPositiveClickListener = onPositiveClickListener
        return this
    }

    init {
        binding.deleteOkBtn.setOnClickListener {
            if (onPositiveClickListener != null) {
                onPositiveClickListener!!.onClick(this@DeleteDialog)
            }
        }
        binding.deleteCancelBtn.setOnClickListener { cancel() }
        setView(binding.root)
    }
}