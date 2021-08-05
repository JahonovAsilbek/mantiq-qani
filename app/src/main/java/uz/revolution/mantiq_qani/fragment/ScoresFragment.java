package uz.revolution.mantiq_qani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import uz.revolution.mantiq_qani.GameActivity;
import uz.revolution.mantiq_qani.R;
import uz.revolution.mantiq_qani.adapter.FragmentAdapter;
import uz.revolution.mantiq_qani.dataBase.Database;
import uz.revolution.mantiq_qani.model.MyData;

import java.util.ArrayList;

public class ScoresFragment extends Fragment {
    private ListView listView;
    private ArrayList<MyData> data;
    private FragmentAdapter adapter;
    private Database database = Database.getDatabase();
    private Button cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_fragment, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.listview);
        cancel = view.findViewById(R.id.scores_cancel_btn);
        loadData();
        adapter = new FragmentAdapter(data);
        listView.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void loadData() {
        data = new ArrayList<>();
        data = database.getUsersData();
    }
}
