package uz.revolution.mantiq_qani.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import uz.revolution.mantiq_qani.R
import uz.revolution.mantiq_qani.dialogs.WinDialog

class WinDialog(context: Context?) : AlertDialog(context) {
    private var onPositiveClickListener: OnPositiveClickListener? = null
    private var onPlayAgainClickListener: OnPlayAgainClickListener? = null
    private val text_score: TextView
    private val text_time: TextView
    private val ok_btn: Button
    private val play_again_button: Button

    @SuppressLint("SetTextI18n")
    fun setText(score: Int, time: String) {
        Log.d("AAAA", "setText: $score")
        if (score != 0) {
            text_score.text = "Ваше очко: $score"
            text_time.text = "Ваше время: $time"
        } else {
            text_score.text = "Ваше очко: " + 0
            text_time.text = "Ваше время: " + 0
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun build() {
        setCancelable(false)
        create()
        show()
    }

    interface OnPositiveClickListener {
        fun onClick(winDialog: WinDialog)
    }

    fun setOnPositiveClickListener(onPositiveClickListener: OnPositiveClickListener): WinDialog {
        this.onPositiveClickListener = onPositiveClickListener
        return this
    }

    interface OnPlayAgainClickListener {
        fun onClick(winDialog: WinDialog)
    }

    fun setOnPlayAgainClickListener(onPlayAgainClickListener: OnPlayAgainClickListener): WinDialog {
        this.onPlayAgainClickListener = onPlayAgainClickListener
        return this
    }

    init {
        val view = LayoutInflater.from(getContext()).inflate(R.layout.win_dialog, null, false)
        text_score = view.findViewById(R.id.score_show)
        text_time = view.findViewById(R.id.score_time)
        ok_btn = view.findViewById(R.id.score_show_ok_btn)
        play_again_button = view.findViewById(R.id.score_show_play_again_btn)
        ok_btn.setOnClickListener {
            if (onPositiveClickListener != null) {
                onPositiveClickListener!!.onClick(this@WinDialog)
            }
        }
        play_again_button.setOnClickListener {
            if (onPlayAgainClickListener != null) {
                onPlayAgainClickListener!!.onClick(this@WinDialog)
            }
        }
        setView(view)
    }
}