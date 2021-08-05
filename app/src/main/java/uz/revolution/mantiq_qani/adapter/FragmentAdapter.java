package uz.revolution.mantiq_qani.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import uz.revolution.mantiq_qani.R;
import uz.revolution.mantiq_qani.model.MyData;

import java.util.ArrayList;

public class FragmentAdapter extends BaseAdapter {

    private ArrayList<MyData> data;

    public FragmentAdapter(ArrayList<MyData> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    @Override
    public MyData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scores, parent, false);

        TextView name, score, time;

        name = view.findViewById(R.id.scores_name);
        score = view.findViewById(R.id.scores_count);
        time = view.findViewById(R.id.scores_time);


        MyData myData = (MyData) getItem(position);

        name.setText(myData.getName());
        score.setText(myData.getScore());
        time.setText(myData.getTime());

        return view;
    }
}
