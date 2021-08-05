package com.example.mantiq_qani;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantiq_qani.manager.MyManager;
import com.example.mantiq_qani.model.MyModel;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private Button hint, deleteLetter;
    private TextView currentScore, totalScore, level;
    private MyManager myManager;
    private ViewGroup  variantGroup,answerGroup,imageGroup;
    private ArrayList<MyModel> myModel;
    private int totalCount;
    private int d=1;
    private int h=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        loadView();
        loadData();
        myManager = new MyManager(myModel);
        loadDataToView();
    }

    private void loadView() {
        answerGroup=findViewById(R.id.answer_group);
        variantGroup=findViewById(R.id.variant_group);
        hint =findViewById(R.id.hint);
        deleteLetter = findViewById(R.id.delete_letter);
        currentScore=findViewById(R.id.current_score);
        totalScore=findViewById(R.id.total_score);
        level=findViewById(R.id.level);
        imageGroup=findViewById(R.id.image_group);
    }

    private void loadDataToView() {

        totalCount = 0;
//        enableClicks();

        for (int i = 0; i < imageGroup.getChildCount(); i++) {
            ImageView view = (ImageView) imageGroup.getChildAt(i);
            if (i < myManager.getImages().size()) {
                view.setImageResource(myManager.getImages().get(i));
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }

        for (int i = 0; i < answerGroup.getChildCount(); i++) {
            Button button = (Button) answerGroup.getChildAt(i);
            button.setText("");
            if (i < myManager.getAnswerLength()) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button button = (Button) variantGroup.getChildAt(i);
            button.setVisibility(View.VISIBLE);
            button.setText(String.valueOf(myManager.getVariants().charAt(i)));
            button.setTag(String.valueOf(i));
        }
    }

    public void OnAnswerClick(View view) {
        Button button = (Button) view;
        undoLetter(button.getText().toString());
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button variant = (Button) variantGroup.getChildAt(i);
            if (variant.getTag().toString().equals(button.getTag().toString())) {
                button.setTag("");
                button.setText("");
                variant.setVisibility(View.VISIBLE);
                totalCount--;
            }
        }
    }

    public void hint(View view) {
        for (Integer.parseInt(String.valueOf(h)); h <= 2; ) {
            for(int position = 0; position < answerGroup.getChildCount(); position ++) {
                Button answer = (Button) answerGroup.getChildAt(position);
                if (answer.getText().toString().equals("") && answer.getVisibility() == View.VISIBLE) {
                    answer.setText(String.valueOf(myModel.get(myManager.getLevel()).getAnswer().charAt(position)));
                    variantGroup.getChildAt(findLetter(position)).setVisibility(View.INVISIBLE);
                    myManager.setCurrentScore(myManager.getCurrentScore()-2);
                    currentScore.setText(String.valueOf(myManager.getCurrentScore()));
                    answer.setTag(variantGroup.getChildAt(myManager.getLevel()).getTag().toString());
                    totalCount++;
                    break;
                }
            }
            if (totalCount == myManager.getAnswerLength()) {
                checkWin();
            }
            totalScore.setText(String.valueOf(myManager.getTotalScore()));
            h++;
            break;
        }
    }

    public void deleteLetter(View view) {
        for (Integer.parseInt(String.valueOf(d)); d <= 2;) {
            for (int position = 0; position < variantGroup.getChildCount(); position++) {
                Button btn = (Button) variantGroup.getChildAt(position);
                if (!btn.getText().toString().equalsIgnoreCase("")) {
                    variantGroup.getChildAt(findWrongLetter(position)).setVisibility(View.INVISIBLE);
                    myManager.setCurrentScore(myManager.getCurrentScore()-2);
                    currentScore.setText(String.valueOf(myManager.getCurrentScore()));
                    break;
                }
            }
            d++;
            break;
        }
    }

    public int findWrongLetter(int position) {
        int a=0;
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button btn = (Button)variantGroup.getChildAt(i);
            if (btn.getVisibility()==View.VISIBLE && !myModel.get(myManager.getLevel()).getAnswer().contains(btn.getText().toString())) {
                a=i;
                break;
            }
        }
        return a;
    }

    public int findLetter(int position) {
        int a = 0;
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button btn = (Button)variantGroup.getChildAt(i);
            if (btn.getVisibility() == View.VISIBLE &&
                    btn.getText().toString().equals(myModel.get(myManager.getLevel()).getAnswer().charAt(position) + "")) {
                a = i;
            }
        }
        return a;
    }



    public void OnVariantClick(View view) {
        Button button = (Button) view;
        for (int i = 0; i < answerGroup.getChildCount(); i++) {
            Button answer = (Button) answerGroup.getChildAt(i);
            if (answer.getText().toString().equals("") && answer.getVisibility() == View.VISIBLE) {
                answer.setText(button.getText().toString());
                button.setVisibility(View.INVISIBLE);
                answer.setTag(button.getTag().toString());
                totalCount++;
                break;
            }
        }

        Log.i("SHSH", "OnVariantClick: " + totalCount);

        if (totalCount == myManager.getAnswerLength()) {
            checkWin();
        }

    }

    private void undoLetter(String letter) {
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button btn = (Button)variantGroup.getChildAt(i);
            if (btn.getVisibility() == View.INVISIBLE && btn.getText().toString().equals(letter)) {
                btn.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void checkWin() {

        String s = "";
        for (int i = 0; i < answerGroup.getChildCount(); i++) {

            Button button = (Button) answerGroup.getChildAt(i);

            s = s.concat(button.getText().toString());
        }

        if (myManager.checkAnswer(s)) {
            Toast.makeText(this, "Win", Toast.LENGTH_SHORT).show();
            level.setText(String.valueOf(myManager.getLevel()+1));
            currentScore.setText(String.valueOf(myManager.getCurrentScore()));
            totalScore.setText(String.valueOf(myManager.getTotalScore()));
            if (!myManager.isEnd()) {
                loadDataToView();
                d=1;
                h=1;
            }else {
                level.setText(String.valueOf(myManager.getLevel()));
                Toast.makeText(this,"Game Over",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lose", Toast.LENGTH_SHORT).show();
        }

    }



    private void loadData() {
        myModel = new ArrayList<>();

        ArrayList<Integer> apple = new ArrayList<>();
        apple.add(R.drawable.apple1);
        apple.add(R.drawable.apple2);
        apple.add(R.drawable.apple3);
        apple.add(R.drawable.apple4);

        myModel.add(new MyModel("dipapholen","apple",apple));

        ArrayList<Integer> bukhara = new ArrayList<>();
        bukhara.add(R.drawable.bukhara1);
        bukhara.add(R.drawable.bukhara2);
        bukhara.add(R.drawable.bukhara3);
        bukhara.add(R.drawable.bukhara4);
        myModel.add(new MyModel("ахбрадузян","бухара",bukhara));

        ArrayList<Integer> height = new ArrayList<>();
        height.add(R.drawable.height1);
        height.add(R.drawable.height2);
        height.add(R.drawable.height3);
        height.add(R.drawable.height4);
        myModel.add(new MyModel("аывсодтзян","высота",height));

        ArrayList<Integer> kiwi = new ArrayList<>();
        kiwi.add(R.drawable.kivi1);
        kiwi.add(R.drawable.kivi2);
        kiwi.add(R.drawable.kivi3);
        kiwi.add(R.drawable.kivi4);
        myModel.add(new MyModel("ицткисрувн", "киви", kiwi));

        ArrayList<Integer> kran = new ArrayList<>();
        kran.add(R.drawable.kran1);
        kran.add(R.drawable.kran2);
        kran.add(R.drawable.kran3);
        kran.add(R.drawable.kran4);
        myModel.add(new MyModel("вроятйдакн", "кран", kran));

        ArrayList<Integer> music = new ArrayList<>();
        music.add(R.drawable.music1);
        music.add(R.drawable.music2);
        music.add(R.drawable.music3);
        music.add(R.drawable.music4);
        myModel.add(new MyModel("димсукатзы", "музыка", music));

        ArrayList<Integer> ruchka = new ArrayList<>();
        ruchka.add(R.drawable.ruchka1);
        ruchka.add(R.drawable.ruchka2);
        ruchka.add(R.drawable.ruchka3);
        ruchka.add(R.drawable.ruchka4);
        myModel.add(new MyModel("чдкаувлюер", "ручка", ruchka));

        ArrayList<Integer> sport = new ArrayList<>();
        sport.add(R.drawable.sport1);
        sport.add(R.drawable.sport2);
        sport.add(R.drawable.sport3);
        sport.add(R.drawable.sport4);
        myModel.add(new MyModel("маопшстинр", "спорт", sport));

        ArrayList<Integer> water = new ArrayList<>();
        water.add(R.drawable.suv1);
        water.add(R.drawable.suv2);
        water.add(R.drawable.suv3);
        water.add(R.drawable.suv4);
        myModel.add(new MyModel("моадкнлрве", "вода", water));

        ArrayList<Integer> letter = new ArrayList<>();
        letter.add(R.drawable.xat1);
        letter.add(R.drawable.xat2);
        letter.add(R.drawable.xat3);
        letter.add(R.drawable.xat4);
        myModel.add(new MyModel("оьлияпмаст", "письмо", letter));


    }
}
