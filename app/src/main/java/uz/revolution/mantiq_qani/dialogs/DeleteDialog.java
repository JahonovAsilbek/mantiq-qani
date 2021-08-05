package uz.revolution.mantiq_qani.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import uz.revolution.mantiq_qani.R;

public class DeleteDialog extends AlertDialog {

    private OnPositiveClickListener onPositiveClickListener;

    public DeleteDialog(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null, false);

        Button ok, cancel;

        ok = view.findViewById(R.id.delete_ok_btn);
        cancel = view.findViewById(R.id.delete_cancel_btn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPositiveClickListener != null) {
                    onPositiveClickListener.onClick(DeleteDialog.this);
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        setView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DeleteDialog build() {
        this.create();
        this.show();
        return this;
    }

    public interface OnPositiveClickListener{
        void onClick(DeleteDialog dialog);
    }

    public DeleteDialog setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }
}
