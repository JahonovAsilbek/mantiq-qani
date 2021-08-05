package uz.revolution.mantiq_qani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uz.revolution.mantiq_qani.GameActivity;
import uz.revolution.mantiq_qani.R;

public class InfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.info_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button cancel;
        TextView info;
        cancel = view.findViewById(R.id.info_cancel_btn);
        info = view.findViewById(R.id.info_text);

        String infoText = null;
        if (getArguments() != null) {
            infoText = getArguments().getString("comment");
        }


        if (infoText != null) {
            info.setText(infoText);
        }else {
            info.setText("no commentary");
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }
}
