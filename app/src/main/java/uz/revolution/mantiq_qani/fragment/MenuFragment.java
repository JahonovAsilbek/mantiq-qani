package uz.revolution.mantiq_qani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import uz.revolution.mantiq_qani.GameActivity;
import uz.revolution.mantiq_qani.R;

public class MenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.menu_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button scores, settings, about, cancel;
        scores = view.findViewById(R.id.scores);
        settings = view.findViewById(R.id.settings);
        about = view.findViewById(R.id.about);
        cancel = view.findViewById(R.id.cancel);

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoresFragment fragment = new ScoresFragment();

                ((AppCompatActivity) getActivity())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, fragment)
                        .addToBackStack("fragment_1")
                        .commit();

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment fragment = new SettingsFragment();

                ((AppCompatActivity) getActivity())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, fragment)
                        .addToBackStack("fragment_1")
                        .commit();

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutFragment fragment = new AboutFragment();

                ((AppCompatActivity)getActivity())
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, fragment)
                        .addToBackStack("fragment_1")
                        .commit();
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
