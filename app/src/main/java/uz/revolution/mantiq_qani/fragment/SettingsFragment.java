package uz.revolution.mantiq_qani.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import uz.revolution.mantiq_qani.GameActivity;
import uz.revolution.mantiq_qani.R;
import uz.revolution.mantiq_qani.dataBase.Database;
import uz.revolution.mantiq_qani.dialogs.DeleteDialog;

public class SettingsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.settings_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton deleteBtn;
        Button cancel;

        deleteBtn = view.findViewById(R.id.delete_scores_btn);
        cancel = view.findViewById(R.id.settings_cancel_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                DeleteDialog dialog = new DeleteDialog(getContext());

                dialog.setOnPositiveClickListener(new DeleteDialog.OnPositiveClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(DeleteDialog dialog) {
                        Database database = Database.getDatabase();
                        database.deleteAllData();
                        dialog.cancel();
                    }
                })
                .build();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

}
