package uz.revolution.mantiq_qani.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import uz.revolution.mantiq_qani.R;
import uz.revolution.mantiq_qani.dataBase.Database;

public class StartDialog extends Dialog {

    private OnStartClickListener onStartClickListener;
    private View view;
    private Database database = Database.getDatabase();

    public StartDialog(Context context) {
        super(context);
            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.start_dialog, null, false);

        setContentView(view);

        bindView();
    }

    public void bindView() {
        final EditText name;
        Button start;

        name = view.findViewById(R.id.edit_name);
        start = view.findViewById(R.id.start_btn);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (onStartClickListener != null) {
                        onStartClickListener.onClick(StartDialog.this, name.getText().toString());
                    }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StartDialog build() {
        setCancelable(false);
        this.create();
        this.show();
        return this;
    }

    public interface OnStartClickListener{
        void onClick(StartDialog dialog, String name);
    }

    public StartDialog setOnStartClickListener(OnStartClickListener onStartClickListener) {
        this.onStartClickListener = onStartClickListener;
        return this;
    }
}
