package uz.revolution.mantiq_qani.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;

import uz.revolution.mantiq_qani.R;


public class WinDialog extends AlertDialog {

    private OnPositiveClickListener onPositiveClickListener;
    private OnPlayAgainClickListener onPlayAgainClickListener;
    private TextView text_score, text_time;
    private Button ok_btn, play_again_button;

    public WinDialog( Context context) {
        super(context);

       View view = LayoutInflater.from(getContext()).inflate(R.layout.win_dialog, null, false);

        text_score = view.findViewById(R.id.score_show);
        text_time = view.findViewById(R.id.score_time);

        ok_btn = view.findViewById(R.id.score_show_ok_btn);
        play_again_button = view.findViewById(R.id.score_show_play_again_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositiveClickListener != null) {
                    onPositiveClickListener.onClick(WinDialog.this);
                }
            }
        });

        play_again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlayAgainClickListener != null) {
                    onPlayAgainClickListener.onClick(WinDialog.this);
                }
            }
        });



        setView(view);
    }

    @SuppressLint("SetTextI18n")
    public void setText(int score,String time) {
        Log.d("AAAA", "setText: " + score);
        if (score != 0) {
            this.text_score.setText("Ваше очко: "+score);
            this.text_time.setText("Ваше время: "+time);
        }else {
            this.text_score.setText("Ваше очко: "+0);
            this.text_time.setText("Ваше время: "+0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void build() {
        this.setCancelable(false);
        this.create();
        this.show();
    }

    public interface OnPositiveClickListener {
        void onClick(WinDialog winDialog);
    }

    public WinDialog setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    public  interface OnPlayAgainClickListener{
        void onClick(WinDialog winDialog);
    }

    public WinDialog setOnPlayAgainClickListener(OnPlayAgainClickListener onPlayAgainClickListener) {
        this.onPlayAgainClickListener = onPlayAgainClickListener;
        return this;
    }
}
